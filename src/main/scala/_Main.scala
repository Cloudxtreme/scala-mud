import akka.actor._

object _Main extends App {
  implicit val system: ActorSystem = ActorSystem("MUD")

  // start the server by creating the actor
  system actorOf Props[Server]
}
