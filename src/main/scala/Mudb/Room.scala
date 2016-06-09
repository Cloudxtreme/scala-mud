package Mudb

import slick.driver.SQLiteDriver.api._

case class Room(id: Int) {
  // TODO: room methods
}

class RoomTable(tag: Tag) extends Table[Room](tag, "room") {
  def id = column[Int]("id")

  // `id` is a foreign key into the entity table
  def entityKey = foreignKey("id", id, Master.entities)(_.id)

  // match the type parameter of the table
  def * = id <> (Room, Room.unapply)
}
