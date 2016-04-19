package com.carlossouza

import java.sql.Timestamp
import java.util.Calendar

import com.carlossouza.dao._
import com.carlossouza.models.UserRow
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

  val wsClient = NingWSClient()
  val APIKey = "4d6f643856f412a263841233916952"
  val meetupSource = new MeetupSource(wsClient, APIKey)

  val futureResult = meetupSource.getCities   //.getMembers(18804321) //.getCities

  /*futureResult.onComplete {
    case Success(m) => {
      println(m)
      //println(Json.prettyPrint(m))
      wsClient.close()
    }
    case Failure(t) => {
      println("An error has occured: " + t.getMessage)
      wsClient.close()
    }
  }*/
  futureResult.map { result =>
    println(result)
    wsClient.close()
  }

  Await.result(futureResult, Duration.Inf)


  val currentTimestamp = new Timestamp(Calendar.getInstance.getTime.getTime)

  println("\nBeginning MySQL part")
  val db = Database.forConfig("localhost")
  val userDAO = new UserDAO(db)
  val f = userDAO.deleteById(7)
  f.map { result =>
    println(result)
    db.close()
  }
  Await.ready(f, Duration.Inf)

}
