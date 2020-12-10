package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.AppOptions
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger

object NewStoriesCommand extends StoriesCommandObject {
  override def help(): String = buildHelp(name, "Shows new stories from HackerNews", helpOptions)
  override val name: String = "new-stories"
  override lazy val data: Array[Long] = ApiClient.getNewStories
}

class NewStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing NewStoriesCommand")
    execute(NewStoriesCommand)
  }
}