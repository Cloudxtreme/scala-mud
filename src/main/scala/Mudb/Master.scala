package Mudb

import slick.driver.SQLiteDriver.api._

object Master {
  val entities = TableQuery[EntityTable]
  val rooms = TableQuery[RoomTable]
  val exits = TableQuery[ExitTable]


}
