import akka.actor._
import slick.driver.SQLiteDriver.api._

import scala.util._

object Driver extends App {
  implicit val system = ActorSystem("MUD")
  implicit val database = Database.forURL(s"jdbc:sqlite:${Config.db}", "org.sqlite.JDBC")
  implicit val ec = system.dispatcher

  // example room lookup
  Mudb.Master.getEntity(1).onComplete {
    case Success(x) => println(x.head.long)
    case Failure(e) => println(e)
  }

  // start the server
  val server = system actorOf Props[Server]
}
