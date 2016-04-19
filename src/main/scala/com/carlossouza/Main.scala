package com.carlossouza

import play.api.libs.json.JsValue
import play.api.libs.ws.ning.{NingWSClient}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import play.api.libs.json.Json

/**
  * Created by carlossouza on 4/16/16.
  */

object Main extends App {

  println(args)

  val wsClient = NingWSClient()
  val APIKey = "4d6f643856f412a263841233916952"
  val meetupSource = new MeetupSource(wsClient, APIKey)

  val futureResult = meetupSource.getCities   //.getMembers(18804321) //.getCities

  futureResult.onComplete {
    case Success(m) => {
      println(m)
      //println(Json.prettyPrint(m))
      wsClient.close()
    }
    case Failure(t) => {
      println("An error has occured: " + t.getMessage)
      wsClient.close()
    }
  }
}
