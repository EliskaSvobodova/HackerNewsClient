package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.AppOptions


object NotFoundCommand extends CommandObject {
  override def help(): String = ""
  override val name: String = "Command not found"
}

class NotFoundCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  override def execute(): Unit = {
    println(NotFoundCommand.name + "\n")
    println("Try \"hackernewsclient --help\" for possible options")
  }
}
