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
    logger.info("Executing BestStoriesCommand")
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => page = pageNum.toInt
        case pageSizeRe(pageSizeNum) => pageSize = pageSizeNum.toInt
        case "--help" => BestStoriesCommand.help()
        case unknown => printUnknownOption(unknown, BestStoriesCommand.name)
      }
    }
    renderPage(ApiClient.getBestStories())
  }
}