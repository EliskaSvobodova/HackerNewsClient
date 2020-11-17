package cz.cvut.fit.bioop.hackernewsclient.commands

trait CommandObject {
  val name: String
  def help(): String
}