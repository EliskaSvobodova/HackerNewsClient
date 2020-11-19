package cz.cvut.fit.bioop.hackernewsclient.commands
import cz.cvut.fit.bioop.hackernewsclient.api.{ApiClient, RequestUrl, ResponseParser}
import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, Logger}


object TopStoriesCommand extends CommandObject {
  override def help(): String = "Shows top stories from HackerNews"
  override val name: String = "top-stories"
}

class TopStoriesCommand(val appOptions: AppOptions, val commandOptions: Array[String]) extends StoriesCommand {
  private val logger = Logger(getClass.getSimpleName)

  override def execute(): Unit = {
    if(commandOptions.length == 0) {
      printTitles(ApiClient.getTopStories())
      return
    }
    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => printPage(pageNum.toInt, ApiClient.getTopStories())
        case "--help" => TopStoriesCommand.help()
        case unknown => printUnknownOption(unknown)
      }
    }
  }

  def printUnknownOption(option: String): Unit = {
    println("top-stories - unknown option \"" + option + "\"")
    println("Try \"hackernewsclient top-stories --help\" for possible options")
  }
}
