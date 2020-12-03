package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger}


object TopStoriesCommand extends StoriesCommandObject {
  override def help(): String = buildHelp(name, "Shows top stories from HackerNews", helpOptions)
  override val name: String = "top-stories"
  override lazy val data: Array[Long] = ApiClient.getTopStories
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing TopStoriesCommand")
    execute(TopStoriesCommand)
  }
}
