package Mudb

import play.api.libs.json._
import play.api.libs.json.Reads._

import scala.io.Source

object Master {
  private var prototypes = Map[String,JsObject]()

  // load a json file that contains an array of object prototypes
  def loadObjects(source: String): Unit = {
    val ns = source.substring(0, source.lastIndexOf('.')).split('/').tail.mkString("/")
    val json = Source.fromFile(getClass.getResource(source).toURI).mkString
    val objects = (Json parse json).as[Array[JsObject]]

    // each object needs to have a unique name
    objects foreach { obj =>
      (obj \ "name").asOpt[String] foreach { name => prototypes += s"$ns/$name" -> obj }
    }
  }

  // create a new instance of a prototype object
  def clone[T <: Entity ](path: String, in: Option[Entity] = None): Option[T] = {
    (prototypes get path) map (Json.reads[T]
  }

  //
  implicit def readsEntity(json: JsValue): JsResult[Entity] =
}
