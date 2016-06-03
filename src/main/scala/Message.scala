sealed trait Message

// server messages
case class Broadcast(client: Option[Client], msg: String) extends Message


// player messages


// room messages
case class Enter(player: Player) extends Message
case class Exit(player: Player) extends Message
case class Say(player: Player, msg: String) extends Message