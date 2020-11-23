package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.User
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger}

object UserCommand extends CommandObject {
  override def help(): String = "Fetches user's data"
  override val name: String = "user"
}

class UserCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)

  private val idRe = "--id=([a-zA-Z0-9_]+)".r

  private var id = ""
  private var stories = false

  override def execute(): Unit = {
    logger.info("Executing UserCommand")

    for(option <- commandOptions) {
      option match {
        case idRe(givenId) => id = givenId
        case "--stories" => stories = true
        case "--help" => UserCommand.help()
        case unknown => printUnknownOption(unknown, UserCommand.name)
      }
    }
    if(id.length == 0){
      println("user command: " + Console.RED + "Missing compulsory option --id=[value]" + Console.RESET)
      return
    }
    val user = ApiClient.getUser(id)
    render(user)
  }

  private def render(user: User): Unit = {
    Renderer.renderUser(user)
    if(stories) renderStories(user)
  }

  private def renderStories(user: User): Unit = {
    for(itemId <- user.submitted) {
      val item = ApiClient.getItem(itemId)
      if(!item.deleted && item.itemType == "story") {
        Renderer.renderItem(item)
        println()
      }
    }
  }
}
