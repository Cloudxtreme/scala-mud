import akka.actor._
import akka.io.Tcp._
import fansi._

import scala.io.Source

class Client(val player: Mudb.Player) extends Actor {
  def receive: Actor.Receive = {
    case Received(data) =>
      player write (Config.welcome getOrElse "Scala-MUD")
      player write Bold.On("login: ")

      // switch to the login handler
      context become login

    case PeerClosed =>
      context stop self
  }

  def login: Actor.Receive = {
    case Received(name) if name.utf8String.trim.isEmpty =>
      player write Bold.On("login: ")

    case Received(name) =>
      player.prompt()

      // switch to the user state
      context become user

    case PeerClosed =>
      context stop self
  }

  def user: Actor.Receive = {
    case Received(cmd) =>
      ()

      // send the command prompt
      player.prompt()

    case PeerClosed =>
      context stop self
  }
}
