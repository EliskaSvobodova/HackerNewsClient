package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger, RequestUrl}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  override def name: String = "top-stories"

  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty")
    logger.info("GET " + topStoriesIds)
  }
}
