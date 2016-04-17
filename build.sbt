name := """mlg"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.typesafe.play" %% "play-ws" % "2.4.3"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

