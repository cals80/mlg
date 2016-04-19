package com.carlossouza.models

import java.sql.Timestamp
import play.api.libs.functional.syntax._
import play.api.libs.json._

trait Formats {
  implicit val rds: Reads[Timestamp] = (__ \ "time").read[Long].map{ long => new Timestamp(long) }
  implicit val wrs: Writes[Timestamp] = (__ \ "time").write[Long].contramap{ (a: Timestamp) => a.getTime }
  implicit val fmt: Format[Timestamp] = Format(rds, wrs)

  implicit val CityRowFormat = Json.format[CityRow]
  implicit val GroupRowFormat = Json.format[GroupRow]
  implicit val MemberRowFormat = Json.format[MemberRow]
  implicit val UserRowFormat = Json.format[UserRow]
}