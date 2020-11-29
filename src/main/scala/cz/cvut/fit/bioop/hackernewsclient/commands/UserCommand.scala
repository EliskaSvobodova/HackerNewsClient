package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException, Logger, OutputService}

import scala.collection.immutable.ListMap
import scala.collection.mutable

object UserCommand extends CommandObject {
  override def help(): String = buildHelp(name, "Fetches user's data", ListMap(
      "--id=[value]" -> "username of the user to fetch, value cannot be empty and has to contain only a-z, A-Z, 0-9, _",
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
  case class Options(id: String, display: mutable.TreeSet[String])

  override def execute(): Unit = {
    logger.info("Executing UserCommand")
    try{
      val options = getOptions
      OutputService.displayUser(options.id, options.display)
    } catch {
      case e: IllegalArgumentException =>
        Renderer.displayError(e.getMessage)
      case _: HelpException =>
        Renderer.renderHelp(UserCommand.help())
        logger.info("UserCommand help")
    }
  }

  def getOptions: Options = {
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
          throw new HelpException
        case unknown =>
          logger.error("Unknown option \"" + unknown + "\"")
          throw new IllegalArgumentException("Unknown option \"" + unknown + "\", " +
            "try hackernewsclient user --help for possible options")
      }
    }

    if(id.length == 0){
      logger.error("Missing compulsory option --id=[value]")
      throw new IllegalArgumentException("Missing compulsory option --id=[value], " +
        "fill a user name instead of [value]")
    }

    Options(id, display)
  }
}
