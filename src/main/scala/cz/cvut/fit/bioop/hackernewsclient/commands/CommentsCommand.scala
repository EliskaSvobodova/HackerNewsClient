package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger
import cz.cvut.fit.bioop.hackernewsclient.services.ItemService
import cz.cvut.fit.bioop.hackernewsclient.ui.builders.UiBuilder
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException}

import scala.collection.immutable.ListMap
import scala.util.matching.Regex

object CommentsCommand extends CommandObject {
  override val name: String = "comments"

  override def help(): String = buildHelp(name, "displays comments on specified item", ListMap(
    "--id=[value]" -> "id of an item whose comments you want to see, COMPULSORY OPTION",
    "--help" -> "displays help"
  ))
}

class CommentsCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)
  private val idRe = "--id=([0-9]+)".r
  private val pageRe: Regex = "--page=([0-9]+)".r
  private val pageSizeRe: Regex = "--page-size=([0-9]+)".r

  case class Options(id: String, page: Int, pageSize: Int)

  override def execute(): Unit = {
    logger.info("Executing CommentsCommand")
    try{
      val options = getOptions
      val itemService = new ItemService()
      val mainItem: Item = itemService.display(options.id)
      itemService.displayPage(options.page, options.pageSize, mainItem.kids.map(kid => kid.toString))
    } catch {
      case _: HelpException => UiBuilder.BuildHelpUi(CommentsCommand.help())
      case e: IllegalArgumentException => UiBuilder.BuildErrorUi(e.getMessage)
      case e: NoSuchElementException => UiBuilder.BuildErrorUi(e.getMessage)
    }
  }

  def getOptions: Options = {
    var id: String = ""
    var page = 1
    var pageSize = 10

    for(option <- commandOptions) {
      option match {
        case idRe(itemId) => id = itemId
        case pageRe(pageNum) => page = pageNum.toInt
        case pageSizeRe(pageSizeNum) => pageSize = pageSizeNum.toInt
        case "--help" =>
          logger.info("CommentsCommand help")
          throw new HelpException
        case unknown =>
          logger.error("Unknown option \"" + unknown + "\"")
          throw new IllegalArgumentException("Unknown option \"" + unknown + "\", " +
            "try hackernewsclient user --help for possible options")
      }
    }

    if(id == ""){
      logger.error("Missing compulsory option --id=[value]")
      throw new IllegalArgumentException("Missing compulsory option --id=[value], " +
        "fill a user name instead of [value]")
    }

    Options(id, page, pageSize)
  }
}
