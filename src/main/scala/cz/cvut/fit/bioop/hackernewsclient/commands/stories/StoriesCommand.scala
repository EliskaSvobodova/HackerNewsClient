package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.HelpException
import cz.cvut.fit.bioop.hackernewsclient.commands.Command
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger
import cz.cvut.fit.bioop.hackernewsclient.services.ItemService
import cz.cvut.fit.bioop.hackernewsclient.ui.Ui

import scala.util.matching.Regex

/**
 * Common trait for stories commands
 */
trait StoriesCommand extends Command {
  private val logger = Logger(getClass.getSimpleName)

  private val pageRe: Regex = "--page=([0-9]+)".r
  private val pageSizeRe: Regex = "--page-size=([0-9]+)".r

  case class StoriesOptions(page: Int, pageSize: Int)

  /**
   * Displays stories with given ids
   */
  protected def execute(storiesObj: StoriesCommandObject, storiesIds: Array[String] = Array()): Unit = {
    try{
      val options = getOptions
      val itemService = new ItemService()
      itemService.displayPage(options.page, options.pageSize, storiesIds)
    } catch {
      case _: HelpException => Ui.displayHelp(storiesObj.help())
      case e: IllegalArgumentException => Ui.displayError(e.getMessage)
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
