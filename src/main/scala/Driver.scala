import akka.actor._

object Driver extends App {
  implicit val system: ActorSystem = ActorSystem("MUD")

  // TODO: load object database

  // start the server by creating the actor
  system actorOf Props[Server]
}
