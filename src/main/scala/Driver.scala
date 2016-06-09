import akka.actor._
import slick.driver.SQLiteDriver.api._

import scala.concurrent.ExecutionContext
import scala.util._

object Driver extends App {
  implicit val system: ActorSystem = ActorSystem("MUD")
  implicit val ec: ExecutionContext = system.dispatcher

  // connect to the mud database
  val db = Config.db map (path => Database.forURL(s"jdbc:sqlite:$path", driver="org.sqlite.JDBC"))

  // example room lookup
  val q = Mudb.Master.entities filter { _.id === 1 }
  val p = db.get run q.result

  p.onComplete {
    case Success(x) => println(x.head.long)
    case Failure(e) => println(e)
  }

  // start the server
  val server = system actorOf Props[Server]
}
