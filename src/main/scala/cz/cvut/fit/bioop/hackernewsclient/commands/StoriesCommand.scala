package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import scala.util.matching.Regex

trait StoriesCommand extends Command {
  protected val pageRe: Regex = "--page=([0-9]+)".r
  protected val pageSizeRe: Regex = "--page-size=([0-9]+)".r

  // default values, can change according to command options
  protected var page = 1
  protected var pageSize = 10

  def renderPage(storiesIds: Array[Int]): Unit = {
    if((page - 1) * pageSize > storiesIds.length) {
      println(Console.RED + "There are no more stories on page " + page)
      return
    }
    val range = Range((page - 1) * pageSize, page * pageSize)
    for {
      (numDisplayed, storyId) <- range zip storiesIds
    } yield {
      val item = ApiClient.getItem(storyId)
      print(numDisplayed + ". ")
      Renderer.renderItem(item)
      println()
    }
  }

  def printUnknownOption(option: String, commandName: String): Unit = {
    println(commandName + " - unknown option \"" + option + "\"")
    println("Try \"hackernewsclient " + commandName + " --help\" for possible options")
  }
}
