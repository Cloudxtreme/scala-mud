import play.api.libs.json._

import scala.io.Source

object Config {
  def resource(path: String) = Source.fromFile(getClass.getResource(path).toURI)

  // parse the configuration file
  val json = Json parse resource("/config.json").mkString

  // the port # that the mud will run on
  val port = (json \ "port").asOpt[Int] getOrElse 8001

  // the welcome message of the day text file
  val welcome = (json \ "welcome").asOpt[String] map (resource(_).mkString)

  // location of the database file
  val db =  getClass.getResource((json \ "db").as[String]).getPath
}
