package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger}

object NewStoriesCommand extends CommandObject {
  override def help(): String = "Shows new stories from HackerNews"
  override val name: String = "new-stories"
}

class NewStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    if(commandOptions.length == 0) {
      printTitles(ApiClient.getNewStories())
      return
    }
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => printPage(pageNum.toInt, ApiClient.getNewStories())
        case "--help" => NewStoriesCommand.help()
        case unknown => printUnknownOption(unknown)
      }
    }
  }

  def printUnknownOption(option: String): Unit = {
    println("new-stories - unknown option \"" + option + "\"")
    println("Try \"hackernewsclient new-stories --help\" for possible options")
  }
}