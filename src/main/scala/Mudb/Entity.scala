package Mudb

import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.collection.mutable

case class Entity(name: String, short: String, long: String) extends AnyRef
  with Traversable[Entity]
  with mutable.SetLike[Entity,Entity]
  with mutable.Set[Entity]
{
  override def empty: Entity = new Entity("entity", "an entity", "Just an ordinary entity.")

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
