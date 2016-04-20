package com.carlossouza

import java.sql.Timestamp
import java.util.Calendar

import com.carlossouza.dao._
import com.carlossouza.models.{CityRow, UserRow}
import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
import play.api.libs.json.JsValue
import play.api.libs.ws.ning.{NingWSClient}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import play.api.libs.json.Json

import scala.concurrent.{Future, Await}
import slick.driver.MySQLDriver.api._
import scala.concurrent.duration._

/**
  * Created by carlossouza on 4/16/16.
  */

object Main extends App {

  // Read arguments
  val parser = new scopt.OptionParser[ConfigArguments]("Meetup Lead Generator") {
    head("Meetup Lead Generator", "1.0")
    checkConfig { c =>
      if (c.mode == "") failure("you must enter a command") else success }
    opt[Unit]("save-to-database") abbr "db" action { (_, c) =>
      c.copy(saveToDatabase = true) } text "flag to save to database"
    help("help") text "prints this usage text"
    cmd("cities") required() action { (_, c) =>
      c.copy(mode = "cities") } text "'cities' is a command." children(
      opt[String]('c', "country") required() action { (x, c) =>
        c.copy(country = x) } text "country 2-digit code",
      opt[Int]('m', "max") action { (x, c) =>
        c.copy(max = x) } text "maximum number of cities"
      )
    cmd("groups") required() action { (_, c) =>
      c.copy(mode = "groups") } text "'groups' is a command." children(
      opt[String]('c', "city") required() action { (x, c) =>
        c.copy(city = x) } text "city name",
      opt[Int]('m', "max") action { (x, c) =>
        c.copy(max = x) } text "maximum number of groups",
      opt[String]('q', "query") required() action { (x, c) =>
        c.copy(query = x) } text "query to search (e.g. \"Machine Learning\")"
      )
  }

  // Parse arguments
  parser.parse(args, ConfigArguments()) match {
    case Some(configArguments) =>
      val wsClient      = NingWSClient()
      val APIKey        = ConfigFactory.load().getString("meetup.APIKey")
      val meetupSource  = new MeetupSource(wsClient, APIKey)
      val db            = Database.forConfig("localhost")
      val cityDAO       = new CityDAO(db)

      configArguments.mode match {
        case u if u.startsWith("cities") => {
          // Fetch top cities from Meetup, given a country
          val futureResult = meetupSource.getCities(configArguments.country, configArguments.max)
          futureResult.map { result =>
            result.foreach { c =>
              println(c)
              if (configArguments.saveToDatabase) cityDAO.save(CityRow(c.id, c.city, c.lat, c.lon, c.distance, c.country, c.localized_country_name, c.zip, c.ranking, c.member_count))
            }
            wsClient.close()
          }
          Await.result(futureResult, Duration.Inf)
        }
        case u if u.startsWith("groups") => {
          // Fetch top groups from Meetup, given a city and a query
          val futureResult = meetupSource.findGroups(configArguments.query, configArguments.city, configArguments.max)
          futureResult.map { result =>
            result.foreach { c =>
              println(c)
              //if (configArguments.saveToDatabase) cityDAO.save(CityRow(c.id, c.city, c.lat, c.lon, c.distance, c.country, c.localized_country_name, c.zip, c.ranking, c.member_count))
            }
            wsClient.close()
          }
          Await.result(futureResult, Duration.Inf)
        }
        case _ => {
          println("ERROR")
        }
      }

      // TODO CHANGE THIS LINE: CLOSE DB AFTER AWAIT
      db.close()

    case None =>
      // arguments are bad, error message will have been displayed
      println("An error occurred reading arguments")
  }

}
