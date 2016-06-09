package Mudb

import slick.driver.SQLiteDriver.api._

case class Entity(id: Int, name: String, short: String, long: String) {
  // TODO: entity methods
}

class EntityTable(tag: Tag) extends Table[Entity](tag, "entity") {
  def id = column[Int]("id", O.PrimaryKey)
  def name = column[String]("name")
  def short = column[String]("short")
  def long = column[String]("long")

  // match the type parameter of the table
  def * = (id, name, short, long) <> (Entity.tupled, Entity.unapply)
}
