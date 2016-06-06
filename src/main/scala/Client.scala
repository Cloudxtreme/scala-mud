import akka.actor._
import akka.io.Tcp._
import akka.util._
import fansi._

import scala.io.Source

// every connection is a client
class Client(val socketRef: ActorRef) extends Actor {
  var player: Option[Player] = None

  def receive: Actor.Receive = {
    case Received(data) =>
      write(resource("/motd.txt").mkString)
      write(Bold.On("login: "))

      // switch to the login handler
      context become login

    case PeerClosed =>
      context stop self
  }

  def login: Actor.Receive = {
    case Received(name) if name.utf8String.trim.isEmpty =>
      write(Bold.On("login: "))

    case Received(name) =>
      player = Some(new Player(this, name.utf8String.trim))

      // send the command prompt
      player foreach (_.prompt())

      // switch to the user state
      context become user

    case PeerClosed =>
      context stop self
  }

  def user: Actor.Receive = {
    case Received(cmd) =>
      ()

      // send the command prompt
      player foreach (_.prompt())

    case PeerClosed =>
      context stop self
  }

  // send a message back to the client
  def write(obj: Any) = socketRef ! Write(ByteString(obj.toString))

  // until there is a valid player for the client, it's a ghost
  def isGhost = player.isEmpty

  // find a resource from the project and load it
  def resource(path: String) = Source.fromFile(getClass.getResource(path).toURI)
}
