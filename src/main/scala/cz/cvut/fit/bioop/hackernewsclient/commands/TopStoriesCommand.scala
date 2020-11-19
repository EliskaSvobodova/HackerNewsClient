package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.api.{ApiClient, RequestUrl, ResponseParser}
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger}


object TopStoriesCommand extends CommandObject {
  override def help(): String = "Shows top stories from HackerNews"
  override val name: String = "top-stories"
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends Command {
  private val logger = Logger(getClass.getSimpleName)
  private val pageRe = "--page=([0-9]+)".r

  override def execute(): Unit = {
    if(commandOptions.length == 0) {
      printTitles()
      return
    }
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => printPage(pageNum.toInt)
        case "--help" => TopStoriesCommand.help()
        case unknown => printUnknownOption(unknown)
      }
    }
  }

  def printTitles() = {
    val topStoriesIds = ApiClient.getTopStories()
    val range = Range(1, appOptions.limitOfStories + 1)
    for {
      (numDisplayed, storyId) <- range zip topStoriesIds
    } yield {
      logger.info("Get story " + storyId)
      val item = ApiClient.getItem(storyId)
      println(numDisplayed + ". " + item.title)
    }
  }

  def printPage(pageNum: Int) = {

  }

  def printUnknownOption(option: String) = {
    println("top-stories - unknown option \"" + option + "\"")
    println("Try \"hackernewsclient top-stories --help\" for possible options")
  }
}
