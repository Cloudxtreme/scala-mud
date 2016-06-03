import akka.actor._
import akka.io.Tcp._

import fansi._

class Player(client: Client, name: String) {
  val getClient = client
  val getName = name

  override def toString = getName

  def handler: Actor.Receive = {
    case Received(data) =>
      getClient.context.parent ! Broadcast(Some(getClient), data.utf8String)

    case Broadcast(from, msg) if from.isDefined && from.get != getClient =>
      getClient ! msg
  }

  // send a prompt to the client
  def prompt() = getClient ! Color.Yellow("$ ")
}
