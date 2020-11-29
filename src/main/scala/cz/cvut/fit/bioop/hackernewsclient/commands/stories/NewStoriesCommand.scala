package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.commands.CommandObject
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException, Logger, OutputService}

object NewStoriesCommand extends CommandObject {
  override def help(): String = "Shows new stories from HackerNews"
  override val name: String = "new-stories"
}

class NewStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    logger.info("Executing NewStoriesCommand")
    try{
      val options = getOptions
      OutputService.displayPage(options.page, options.pageSize, ApiClient.getNewStories())
    } catch {
      case _: HelpException => Renderer.renderHelp(NewStoriesCommand.help())
      case e: IllegalArgumentException => Renderer.displayError(e.getMessage)
      case e: NoSuchElementException => Renderer.displayError(e.getMessage)
    }
  }
}