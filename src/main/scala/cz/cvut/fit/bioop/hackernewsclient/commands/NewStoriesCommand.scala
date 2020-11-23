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
    logger.info("Executing NewStoriesCommand")
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => page = pageNum.toInt
        case pageSizeRe(pageSizeNum) => pageSize = pageSizeNum.toInt
        case "--help" => NewStoriesCommand.help()
        case unknown => printUnknownOption(unknown, NewStoriesCommand.name)
      }
    }
    renderPage(ApiClient.getNewStories())
  }
}