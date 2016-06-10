package Mudb

import slick.driver.SQLiteDriver.api._

import scala.concurrent.ExecutionContext

object Master {
  val entities = TableQuery[EntityTable]
  val rooms = TableQuery[RoomTable]
  val exits = TableQuery[ExitTable]

  // compiled queries
  val entityQ = Compiled((id: Rep[Int]) => entities.filter(_.id === id))

  // methods for running queries on the loaded database
  def getEntity(id: Int)(implicit db: Database, ec: ExecutionContext) =
    (db run entityQ(id).result) map { _.headOption }
}
