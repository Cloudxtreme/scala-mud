import java.net.InetSocketAddress

import akka.actor._
import akka.io._
import akka.io.Tcp._
import akka.util._

case class ChatMsg(user: String, msg: String)

class Server extends Actor {
  import context.system

  // start listening for connections
  IO(Tcp) ! Bind(self, new InetSocketAddress("127.0.0.1", 8001))

  // handle incoming connections
  def receive = {
    case Bound(localAddr) =>
      println(s"Listening for connections on port ${localAddr.getPort}...")

    // a new connection, create a client actor and begin...
    case Connected(remote, local) =>
      sender() ! Register(context actorOf Props[Client])

      // a chat message from someone to everyone else
    case msg: ChatMsg =>
      context.children foreach (_ ! msg)
  }
}
