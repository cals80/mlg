package com.carlossouza.dao

import com.carlossouza.models.{CityRow, Tables}
import javax.inject.Inject
import scala.concurrent._
import slick.driver.MySQLDriver
import slick.driver.MySQLDriver.api._

/**
  * Created by carlossouza on 4/19/16.
  */
class CityDAO @Inject() (db: MySQLDriver.backend.Database) {
  def fetchAll(): Future[Seq[CityRow]] = db.run(Tables.City.result)
  def count(): Future[Int] = db.run(Tables.City.length.result)
  def insert(newRow: CityRow): Future[Int] = db.run((Tables.City returning Tables.City.map(_.id)) += newRow)
  def save(maybeNewRow: CityRow): Future[Int] = db.run(Tables.City.insertOrUpdate(maybeNewRow))
  def findById(id: Int): Future[Option[CityRow]] = db.run(Tables.City.filter(_.id === id).result.headOption)
  def deleteById(id: Int): Future[Int] = db.run(Tables.City.filter(_.id === id).delete)
  def update(updatedRow: CityRow): Future[Int] = db.run(Tables.City.filter(_.id === updatedRow.id).update(updatedRow))
}
