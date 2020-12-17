package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.logger.Logger
import cz.cvut.fit.bioop.hackernewsclient.services.{ItemService, UserService}
import cz.cvut.fit.bioop.hackernewsclient.ui.builders.UiBuilder
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException}

import scala.collection.immutable.ListMap
import scala.collection.mutable

object UserCommand extends CommandObject {
  override def help(): String = buildHelp(name, "Fetches user's data", ListMap(
      "--id=[value]" -> "username of the user to fetch, value cannot be empty and has to contain only a-z, A-Z, 0-9, _, COMPULSORY OPTION",
      "--stories or -s" -> "display all stories by specified user",
      "--comments or -c" -> "display all comments by specified user",
      "--jobs or -j" -> "display all jobs by specified user",
      "--polls or -p" -> "display all polls by specified user",
      "--all or -a" -> "display all content by specified user",
      "--help" -> "display help"))
  override val name: String = "user"
}

class UserCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)

  private val idRe = "--id=([a-zA-Z0-9_]*)".r

  case class UserOptions(id: String, display: mutable.TreeSet[String])

  override def execute(): Unit = {
    logger.info("Executing UserCommand")
    try{
      val options = getOptions
      val userService = new UserService()
      val user = userService.display(options.id)
      if(options.display.nonEmpty){
        val itemService = new ItemService()
        itemService.displayIf(user.submitted.map(sub => sub.toString), item => !item.deleted && options.display.contains(item.itemType))
      }
    } catch {
      case _: HelpException => UiBuilder.BuildHelpUi(UserCommand.help())
      case e: IllegalArgumentException => UiBuilder.BuildErrorUi(e.getMessage)
      case e: NoSuchElementException => UiBuilder.BuildErrorUi(e.getMessage)
    }
  }

  def getOptions: UserOptions = {
    var id = ""
    var display = mutable.TreeSet[String]()

    for(option <- commandOptions) {
      option match {
        case idRe(givenId) => id = givenId
        case "--stories" | "-s" => display.add("story")
        case "--comments" | "-c" => display.add("comment")
        case "--jobs" | "-j" => display.add("job")
        case "--polls" | "-p" => display.add("poll")
        case "--all" | "-a" => display = mutable.TreeSet("story", "comment", "job", "poll")
        case "--help" =>
          logger.info("UserCommand help")
          throw new HelpException
        case unknown =>
          logger.error("Unknown option \"" + unknown + "\"")
          throw new IllegalArgumentException("Unknown option \"" + unknown + "\", " +
            "try hackernewsclient user --help for possible options")
      }
    }

    if(id.isEmpty){
      logger.error("Missing compulsory option --id=[value]")
      throw new IllegalArgumentException("Missing compulsory option --id=[value], " +
        "fill a user name instead of [value]")
    }

    UserOptions(id, display)
  }
}
