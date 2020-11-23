package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.User
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger, OutputService}

import scala.collection.mutable

object UserCommand extends CommandObject {
  override def help(): String = "Fetches user's data"
  override val name: String = "user"
}

class UserCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)

  private val idRe = "--id=([a-zA-Z0-9_]+)".r

  private var id = ""
  private var display = mutable.TreeSet[String]()

  override def execute(): Unit = {
    logger.info("Executing UserCommand")

    for(option <- commandOptions) {
      option match {
        case idRe(givenId) => id = givenId
        case "--stories" => display.add("story")
        case "--comments" => display.add("comment")
        case "--jobs" => display.add("job")
        case "--polls" => display.add("poll")
        case "--all" => display = mutable.TreeSet("story", "comment", "job", "poll")
        case "--help" => UserCommand.help()
        case unknown => printUnknownOption(unknown, UserCommand.name)
      }
    }
    if(id.length == 0){
      Renderer.displayError("user command: Missing compulsory option --id=[value]")
      return
    }
    OutputService.displayUser(id, display)
  }
}
