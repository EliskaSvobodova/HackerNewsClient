package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.AppOptions

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  override def name: String = "top-stories"

  override def execute(): Unit = ???
}
