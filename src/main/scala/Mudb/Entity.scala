package Mudb

import play.api.libs.json._

class Entity(prototype: JsObject) extends AnyRef
  with Traversable[Entity]
  with scala.collection.mutable.SetLike[Entity,Entity]
  with scala.collection.mutable.Set[Entity]
{
  override def empty: Entity = new Entity(JsObject(Seq()))

  // get the required name and optional short/long descriptions
  val name = (prototype \ "name").as[String]
  val short = (prototype \ "short").asOpt[String] getOrElse "an object"
  val long = (prototype \ "long").asOpt[String] getOrElse "Just an object."

  // use the short description of the entity as the string conversion
  override def toString = short

  // a tree structure which defines what is inside what
  protected var parent: Option[Entity] = None
  protected var children: List[Entity] = List()

  // if this object has a parent, add it to the parent's child list now
  parent foreach (_.children ::= this)

  // true if this bag contains the entity
  def contains(entity: Entity) = children contains entity

  // create an iterator to walk all the entities in the container
  def iterator = children.iterator

  // add/remove an entity to the container's contents list
  def +=(entity: Entity) = { children ::= entity; entity.parent = Some(this); this }
  def -=(entity: Entity) = { children ::= entity; entity.parent = None; this }
}
