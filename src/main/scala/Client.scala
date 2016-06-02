import akka.actor._
import akka.io.Tcp._
import akka.util._

import fansi._

import scala.io.Source

// every connection is a client
class Client extends Actor {
  def resource(path: String) = Source.fromFile(getClass.getResource(path).toURI)

  // handle messages from the server
  def receive = {
    case Received(data) =>
      welcome()
      loginPrompt()

      // ask the user for credentials
      context become login

      // server stopped
    case PeerClosed => context stop self
  }

  // handle the user picking a login name
  def login: Actor.Receive = {
    case Received(username) if username.utf8String.trim.isEmpty =>
      send("Invalid username!\n")
      loginPrompt()

    case Received(username) =>
      prompt()

      // start the main chat server
      context become chat(username.utf8String.trim)

      // server stopped
    case PeerClosed => context stop self
  }

  // handle user user sending chat messages
  def chat(user: String): Actor.Receive = {
    case Received(msg) =>
      context.parent ! ChatMsg(user, msg.utf8String)

      // wait for another message
      prompt()

      // server stopped
    case PeerClosed => context stop self

      // a chat message from a user
    case ChatMsg(from, msg) if user != from =>
      send(Bold.On(from) + s" says: $msg")
  }

  // write a series of bytes back to the client
  def send(obj: Any) = sender() ! Write(ByteString(obj.toString))

  // send the message of the day
  def welcome() = send(resource("/motd.txt").mkString)

  // send a login prompt to the user
  def loginPrompt() = send(Bold.On("login: "))

  // send a prompt to the client
  def prompt() = send(Color.Yellow("$ "))
}
