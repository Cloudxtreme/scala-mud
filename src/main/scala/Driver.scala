import akka.actor._

object Driver extends App {
  implicit val system: ActorSystem = ActorSystem("MUD")

  // have it load all the object database files
  Mudb.Master.loadObjects("/rooms.json")

  val lobby = Mudb.Master.clone[Mudb.Room]("rooms/Lobby")

  // start the server by creating the actor
  system actorOf Props[Server]
}
