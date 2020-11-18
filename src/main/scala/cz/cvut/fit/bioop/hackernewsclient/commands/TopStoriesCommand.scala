package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger, RequestUrl}
import cz.cvut.fit.bioop.hackernewsclient.ResponseParser


object TopStoriesCommand extends CommandObject {
  override def help(): String = "Shows top stories from HackerNews"
  override val name: String = "top-stories"
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json")
    val range = Range(1, appOptions.limitOfStories + 1)
    val storyIds = topStoriesIds.substring(1, topStoriesIds.length - 1).split(",")
    for {
      (numDisplayed, storyId) <- range zip storyIds
    } yield {
      logger.info("Get story " + storyId)
      val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/item/" + storyId + ".json?print=pretty")
      val title = ResponseParser.getTitle(response)
      println(numDisplayed + ". " + title)
    }
  }
}
