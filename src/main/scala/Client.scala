import akka.actor._
import akka.io.Tcp._
import akka.util._

import fansi._

import scala.io.Source

// every connection is a client
class Client extends Actor {
  var socketRef: Option[ActorRef] = None

  // initial handler
  def receive = {
    case Received(data) =>
      socketRef = Some(sender())

      // send the welcome and prompt
      welcome()
      loginPrompt()

      // ask the user for credentials
      become(login)
  }

  // change the message handler, but retain common handlers
  def become(h: Actor.Receive) =
    context become (h orElse {
      case PeerClosed => context stop self
    })

  // handle the user picking a login name
  def login: Actor.Receive = {
    case Received(username) =>
      if (username.utf8String.trim.isEmpty) {
        sender() ! "Invalid username!\n"
        loginPrompt()
      } else {
        become(new Player(this, username.utf8String.trim).handler)
        prompt()
      }
  }

  // write a series of bytes back to the client
  def !(obj: Any) = socketRef foreach (_ ! Write(ByteString(obj.toString)))

  // send the message of the day
  def welcome() = self ! resource("/motd.txt").mkString

  // send a login prompt to the user
  def loginPrompt() = self ! Bold.On("login: ")

  // find a resource from the project and load it
  def resource(path: String) = Source.fromFile(getClass.getResource(path).toURI)
}
