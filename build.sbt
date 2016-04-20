import java.io.PrintWriter

import scala.io.Source

import java.io.{PrintWriter, File}
import scala.io.Source

import sbt.Keys._
import sbt._

name := """mlg"""

version := "1.0"

lazy val root = (project in file("."))

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest"       %% "scalatest"            % "2.2.4" % "test",
  "com.typesafe.play"   %% "play-ws"              % "2.4.3",
  "com.typesafe.play"   %% "play-slick"           % "2.0.0",
  "com.typesafe.slick"  %% "slick"                % "3.1.1",
  "com.typesafe.slick"  %% "slick-codegen"        % "3.1.1",
  "mysql"               % "mysql-connector-java"  % "5.1.38",
  "org.slf4j"           % "slf4j-nop"             % "1.7.10",
  "com.github.scopt"    %% "scopt"                % "3.4.0"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"


/**
  * TODO
  * Remove the next lines of code and put on a SBT plugin
  */

slick <<= slickCodeGenTask

//sourceGenerators in Compile <+= slickCodeGenTask

lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
  val outputDir = new File("").getAbsolutePath + "/src/main/scala/"
  val username = "root"
  val password = ""
  val url = "jdbc:mysql://localhost/mlg"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val slickDriver = "slick.driver.MySQLDriver"
  val pkg = "com.carlossouza.models"
  toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, username, password), s.log))
  val fname = outputDir + "/com/carlossouza/models/Tables.scala"
  Seq(file(fname))
}



formats := generateFormatsTask

lazy val formats = TaskKey[Unit]("gen-formats")
lazy val generateFormatsTask = {
  /**
    * Modify the Tables.scala auto-generated slick tables to save case class in a separate file
    * Slick 3.0 Json formats will only work if case classes are in separate file
    */
  val originFilePath    = new File(".").getAbsolutePath + "/src/main/scala/com/carlossouza/models/Tables.scala"
  val originFile        = new File(originFilePath)
  val caseClassFilePath = new File(".").getAbsolutePath + "/src/main/scala/com/carlossouza/models/TableRows.scala"
  val caseClassOut      = new PrintWriter(caseClassFilePath , "UTF-8")
  val newTablesFilePath = new File(".").getAbsolutePath + "/src/main/scala/com/carlossouza/models/NewTables.scala"
  val newTablesOut      = new PrintWriter(newTablesFilePath , "UTF-8")
  val newTablesFile     = new File(newTablesFilePath)
  try {
    for (line <- Source.fromFile(originFilePath).getLines()) {
      if (line.trim.startsWith("package")) {
        caseClassOut.print(line + "\n\n")
        newTablesOut.print(line + "\n\n")
      } else {
        if (line.trim.startsWith("case class")) {
          caseClassOut.print(line.trim + "\n")
        } else {
          newTablesOut.print(line + "\n")
        }
      }
    }
  } finally {
    caseClassOut.close()
    newTablesOut.close()
    originFile.delete()
    newTablesFile.renameTo(originFile)
  }
  /**
    * Generates Formats.scala trait, with Json formats for all case classes and correction for timestamp
    * */
  val preset: Seq[String] = Seq(
    "import java.sql.Timestamp\n",
    "import play.api.libs.functional.syntax._\n",
    "import play.api.libs.json._\n\n",
    "trait Formats {\n",
    "  implicit val rds: Reads[Timestamp] = (__ \\ \"time\").read[Long].map{ long => new Timestamp(long) }\n",
    "  implicit val wrs: Writes[Timestamp] = (__ \\ \"time\").write[Long].contramap{ (a: Timestamp) => a.getTime }\n",
    "  implicit val fmt: Format[Timestamp] = Format(rds, wrs)\n\n"
  )
  val formatsFilePath = new File(".").getAbsolutePath + "/src/main/scala/com/carlossouza/models/Formats.scala"
  val formatsOut      = new PrintWriter(formatsFilePath , "UTF-8")
  //val caseClassFilePath = new File(".").getAbsolutePath + "/src/main/scala/com/carlossouza/models/TableRows.scala"

  try {
    for (line <- Source.fromFile(caseClassFilePath).getLines()) {
      if (line.startsWith("package")) {
        formatsOut.print(line + "\n\n")
        preset.foreach(presetLine => formatsOut.print(presetLine))
      } else {
        if (line.startsWith("case class")) {
          val caseClass = line.drop("case class ".length).split("Row")(0) + "Row"
          formatsOut.print("  implicit val " + caseClass + "Format = Json.format[" + caseClass + "]\n")
        }
      }
    }
    formatsOut.print("}")
  } finally {
    formatsOut.close()
  }

}