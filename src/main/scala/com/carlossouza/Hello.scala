package com.carlossouza

import play.api.libs.json.JsValue
import play.api.libs.ws.ning.{NingWSClient}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

/**
  * Created by carlossouza on 4/16/16.
  */

object Hello {
  def main(args: Array[String]): Unit = {
    val wsClient = NingWSClient()
    val futureResult: Future[JsValue] = wsClient.url("http://api.meetup.com/recommended/group_topics").get().map { response => response.json }

    futureResult.onComplete {
      case Success(m) => {
        println(m)
        wsClient.close()
      }
      case Failure(t) => {
        println("An error has occured: " + t.getMessage)
        wsClient.close()
      }
    }
  }
}
