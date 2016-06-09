package Mudb

import akka.actor._
import akka.io.Tcp._
import akka.util._
import fansi._
import play.api.libs.json._

class Player(val ref: ActorRef, prototype: JsObject)(implicit system: ActorSystem) {

  // send data back to the client
  def write(obj: Any) = ref ! Write(ByteString(obj.toString))

  // user prompt
  def prompt() = write(Color.Yellow("> "))

  // when the player is done, kill the ref, which will close the socket
  def quit() = system stop ref
}
