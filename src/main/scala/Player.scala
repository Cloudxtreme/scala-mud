import fansi._

class Player(val client: Client, val name: String) {
  override def toString = name

  // send a prompt to the client
  def prompt() = client write Color.Yellow("> ")
}
