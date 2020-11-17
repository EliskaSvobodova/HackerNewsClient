package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger, RequestUrl}

object TopStoriesCommand extends CommandObject {
  override def help(): String = "Shows top stories from HackerNews"
  override val name: String = "top-stories"
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty")
    logger.info("GET " + topStoriesIds)
  }
}
