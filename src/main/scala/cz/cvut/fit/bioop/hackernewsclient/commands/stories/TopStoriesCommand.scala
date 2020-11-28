package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.commands.CommandObject
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger, OutputService}


object TopStoriesCommand extends CommandObject {
  override def help(): String = "Shows top stories from HackerNews"
  override val name: String = "top-stories"
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing TopStoriesCommand")
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => page = pageNum.toInt
        case pageSizeRe(pageSizeNum) => pageSize = pageSizeNum.toInt
        case "--help" => TopStoriesCommand.help()
        case unknown => printUnknownOption(unknown, TopStoriesCommand.name)
      }
    }
    // renderPage(ApiClient.getTopStories())
    try {
      OutputService.displayPage(page, pageSize, ApiClient.getTopStories())
    } catch {
      case e: IllegalArgumentException => logger.error(e.getMessage)
    }
  }
}
