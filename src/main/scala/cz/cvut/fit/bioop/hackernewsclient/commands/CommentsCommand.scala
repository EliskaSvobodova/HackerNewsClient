package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.services.ItemService
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException, Logger}

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

  case class Options(id: Long, page: Int, pageSize: Int)

  override def execute(): Unit = {
    logger.info("Executing CommentsCommand")
    try{
      val options = getOptions
      val mainItem: Item = ItemService.displayItem(options.id)
      ItemService.displayPageOfItems(options.page, options.pageSize, mainItem.kids)
    } catch {
      case _: HelpException => Renderer.renderHelp(CommentsCommand.help())
      case e: IllegalArgumentException => Renderer.displayError(e.getMessage)
      case e: NoSuchElementException => Renderer.displayError(e.getMessage)
    }
  }

  def getOptions: Options = {
    var id: Long = -1
    var page = 1
    var pageSize = 10

    for(option <- commandOptions) {
      option match {
        case idRe(itemId) => id = itemId.toLong
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

    if(id == -1){
      logger.error("Missing compulsory option --id=[value]")
      throw new IllegalArgumentException("Missing compulsory option --id=[value], " +
        "fill a user name instead of [value]")
    }

    Options(id, page, pageSize)
  }
}
