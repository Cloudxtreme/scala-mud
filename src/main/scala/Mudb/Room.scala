package Mudb

import fansi._
import play.api.libs.json._

case class Room(prototype: JsObject) extends Entity(prototype) {

  // lookup all the exits
  val exits = List()
}
