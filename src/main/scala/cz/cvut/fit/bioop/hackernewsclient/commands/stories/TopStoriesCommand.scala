package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.commands.CommandObject
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException, Logger, OutputService}


object TopStoriesCommand extends CommandObject {
  override def help(): String = "Shows top stories from HackerNews"
  override val name: String = "top-stories"
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing TopStoriesCommand")
    try{
      val options = getOptions
      OutputService.displayPage(options.page, options.pageSize, ApiClient.getTopStories())
    } catch {
      case _: HelpException => Renderer.renderHelp(TopStoriesCommand.help())
      case e: IllegalArgumentException => Renderer.displayError(e.getMessage)
      case e: NoSuchElementException => Renderer.displayError(e.getMessage)
    }
  }
}
