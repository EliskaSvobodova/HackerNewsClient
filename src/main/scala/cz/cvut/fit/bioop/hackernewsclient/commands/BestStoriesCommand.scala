package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger}
import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient

object BestStoriesCommand extends CommandObject {
  override def help(): String = "Shows best stories from HackerNews"
  override val name: String = "best-stories"
}

class BestStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    if(commandOptions.length == 0) {
      printTitles(ApiClient.getBestStories())
      return
    }
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => printPage(pageNum.toInt, ApiClient.getBestStories())
        case "--help" => BestStoriesCommand.help()
        case unknown => printUnknownOption(unknown)
      }
    }
  }

  def printUnknownOption(option: String): Unit = {
    println("best-stories - unknown option \"" + option + "\"")
    println("Try \"hackernewsclient best-stories --help\" for possible options")
  }
}