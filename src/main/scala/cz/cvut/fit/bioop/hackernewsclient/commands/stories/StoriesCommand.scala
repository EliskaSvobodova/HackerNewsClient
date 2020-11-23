package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.OutputService
import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.commands.Command
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import scala.util.matching.Regex

trait StoriesCommand extends Command {
  protected val pageRe: Regex = "--page=([0-9]+)".r
  protected val pageSizeRe: Regex = "--page-size=([0-9]+)".r

  // default values, can change according to command options
  protected var page = 1
  protected var pageSize = 10

  def renderPage(storiesIds: Array[Long]): Unit = {
    if ((page - 1) * pageSize > storiesIds.length) {
      println(
        Console.RED + "There are no more stories on page " + page + Console.RESET)
      return
    }
    OutputService.displayPage(page, pageSize, storiesIds)
  }
}
