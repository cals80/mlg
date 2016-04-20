package com.carlossouza.dao

import com.carlossouza.models.{UserRow, Tables}
import javax.inject.Inject
import scala.concurrent._
import slick.driver.MySQLDriver
import slick.driver.MySQLDriver.api._

/**
  * Created by carlossouza on 4/19/16.
  */
class UserDAO @Inject() (db: MySQLDriver.backend.Database) {
  def fetchAll(): Future[Seq[UserRow]] = db.run(Tables.User.result)
  def count(): Future[Int] = db.run(Tables.User.length.result)
  def insert(newRow: UserRow): Future[Int] = db.run((Tables.User returning Tables.User.map(_.id)) += newRow)
  def save(maybeNewRow: UserRow): Future[Int] = db.run(Tables.User.insertOrUpdate(maybeNewRow))
  def findById(id: Int): Future[Option[UserRow]] = db.run(Tables.User.filter(_.id === id).result.headOption)
  def deleteById(id: Int): Future[Int] = db.run(Tables.User.filter(_.id === id).delete)
  def update(updatedRow: UserRow): Future[Int] = db.run(Tables.User.filter(_.id === updatedRow.id).update(updatedRow))
}
