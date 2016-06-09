package Mudb

import slick.driver.SQLiteDriver.api._

case class Exit(from: Int, name: String, to: Int) {
  // TODO: exit methods
}

case class ExitTable(tag: Tag) extends Table[Exit](tag, "exit") {
  def from = column[Int]("id")
  def name = column[String]("name")
  def to = column[Int]("destination")

  // `id` and `destination` are both foreign keys into the room table
  def fromKey = foreignKey("id", from, Master.rooms)(_.id)
  def toKey = foreignKey("destination", to, Master.rooms)(_.id)

  // match the type parameter of the table
  def * = (from, name, to) <> (Exit.tupled, Exit.unapply)
}
