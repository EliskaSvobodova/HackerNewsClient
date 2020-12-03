package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.commands.Command
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.services.ItemService
import cz.cvut.fit.bioop.hackernewsclient.{HelpException, Logger}

import scala.util.matching.Regex

trait StoriesCommand extends Command {
  private val logger = Logger(getClass.getSimpleName)

  private val pageRe: Regex = "--page=([0-9]+)".r
  private val pageSizeRe: Regex = "--page-size=([0-9]+)".r

  case class StoriesOptions(page: Int, pageSize: Int)

  protected def execute(storiesObj: StoriesCommandObject): Unit = {
    try{
      val options = getOptions
      ItemService.displayPage(options.page, options.pageSize, storiesObj.data)
    } catch {
      case _: HelpException => Renderer.renderHelp(storiesObj.help())
      case e: IllegalArgumentException => Renderer.displayError(e.getMessage)
    }
  }

  def getOptions: StoriesOptions = {
    var page = 1
    var pageSize = 10

    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) =>
          logger.info("Changing page number from " + page + " to " + pageNum)
          page = pageNum.toInt
        case pageSizeRe(pageSizeNum) =>
          logger.info("Changing page size from " + page + " to " + pageSizeNum)
          pageSize = pageSizeNum.toInt
        case "--help" =>
          logger.info("Displaying help for command")
          throw new HelpException
        case unknown =>
          logger.error("\"Unknown option \"" + unknown + "\"")
          throw new IllegalArgumentException("Unknown option \"" + unknown + "\", "
            + "try hackernewsclient --help for possible options")
      }
    }

    StoriesOptions(page, pageSize)
  }
}
