import akka.actor._

class Room extends Actor {
  var players: Set[Player] = Set()

  def receive = {
    case Enter(player) =>
      players foreach (_.getClient ! s"${player.getName} enters.")
      players += player

    case Exit(player) =>
      players foreach (_.getClient ! s"${player.getName} leaves.")
      players -= player

    case Say(player, msg) =>
      players foreach (_.getClient ! s"${player.getName} says: $msg")
  }
}
