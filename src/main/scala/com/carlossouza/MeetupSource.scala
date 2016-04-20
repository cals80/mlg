package com.carlossouza

import com.carlossouza.models.{Member, Group, City}
import play.api.libs.json.JsValue
import play.api.libs.ws.ning.NingWSClient
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import play.api.libs.json.Json

/**
  * Created by carlossouza on 4/17/16.
  */

class MeetupSource(client: NingWSClient, APIKey: String) {
  implicit val cityWrites = Json.format[City]
  implicit val groupWrites = Json.format[Group]
  implicit val memberWrites = Json.format[Member]

  val append = "&key=" + APIKey + "&sign=true"

  def getCities(country: String, numCities: Int): Future[List[City]] = {
    val url = "http://api.meetup.com/2/cities?country=" + country + "&page=" + numCities.toString + append
    client.url(url).get().map { response =>
      (response.json \ "results").as[List[City]]
    }
  }

  def findGroups(query: String, city: String, numGroups: Int, radius: Int = 10): Future[List[Group]] = {
    val url = "http://api.meetup.com/find/groups?location='" + city + "'&radius=" + radius + "&page=" + numGroups + "&text='" + query + "'" + append
    client.url(url).get().map { response =>
      response.json.as[List[Group]]
    }
  }

  def getMembers(groupId: Int): Future[List[Member]] = {
    val url = "http://api.meetup.com/2/members?group_id=" + groupId.toString + append
    client.url(url).get().map { response =>
      (response.json \ "results").as[List[Member]]
    }
  }

}
