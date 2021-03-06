package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.AppOptions
import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClientFactory
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger

object BestStoriesCommand extends StoriesCommandObject {
  override def help(): String = buildHelp(name, "Shows best stories from HackerNews", helpOptions)
  override val name: String = "best-stories"
}

class BestStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing BestStoriesCommand")
    val apiClient = ApiClientFactory()
    super.execute(BestStoriesCommand, apiClient.getBestStories)
  }
}