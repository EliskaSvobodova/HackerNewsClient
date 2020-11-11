package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.AppOptions

class NotFoundCommand(val name: String, val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  override def execute(): Unit = {
    println("Command \"" + name + "\" not found")
    println("Try \"hackernewsclient --help\" for possible options")
  }
}
