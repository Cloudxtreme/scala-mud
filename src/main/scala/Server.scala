import java.net.InetSocketAddress

import akka.actor._
import akka.io._
import akka.io.Tcp._

class Server extends Actor {
  import context.system

  // start listening for connections
  IO(Tcp) ! Bind(self, new InetSocketAddress("127.0.0.1", Config.port))

  // handle incoming connections
  def receive = {
    case Bound(localAddr) =>
      println(s"Listening for connections on port ${localAddr.getPort}...")

    case Connected(remote, local) =>
      sender() ! Register(context actorOf Props(classOf[Client], sender()))
  }
}
