package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.AppOptions
import cz.cvut.fit.bioop.hackernewsclient.ui.Ui


object NotFoundCommand extends CommandObject {
  override def help(): String = ""
  override val name: String = "NotFoundCommand"
}

class NotFoundCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  override def execute(): Unit = {
    Ui.displayError("Command not found. Try \"hackernewsclient --help\" for possible options")
  }
}
