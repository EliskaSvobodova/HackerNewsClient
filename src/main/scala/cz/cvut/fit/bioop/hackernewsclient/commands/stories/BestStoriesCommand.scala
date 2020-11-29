package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.commands.CommandObject
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException, Logger, OutputService}

object BestStoriesCommand extends CommandObject {
  override def help(): String = "Shows best stories from HackerNews"
  override val name: String = "best-stories"
}

class BestStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing BestStoriesCommand")
    try{
      val options = getOptions
      OutputService.displayPage(options.page, options.pageSize, ApiClient.getBestStories())
    } catch {
      case _: HelpException => Renderer.renderHelp(BestStoriesCommand.help())
      case e: IllegalArgumentException => Renderer.displayError(e.getMessage)
      case e: NoSuchElementException => Renderer.displayError(e.getMessage)
    }
  }
}