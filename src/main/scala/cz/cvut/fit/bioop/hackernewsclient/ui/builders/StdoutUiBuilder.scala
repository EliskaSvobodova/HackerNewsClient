package cz.cvut.fit.bioop.hackernewsclient.ui.builders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.ui.html.HtmlConverter

import java.sql.Date


class StdoutUiBuilder extends UiBuilder {
  override def buildItemUi(item: Item): Unit = {
    item.itemType match {
      case "comment" => buildComment(item)
      case "job" => buildJob(item)
      case "poll" => buildPoll(item)
      case _ => buildStory(item)
    }
  }

  override def buildUserUi(user: User): Unit = {
    print("user :     ")
    printBold(user.id + "\n")
    print("created:   ")
    buildTime(user.created)
    print("karma:     ")
    println(user.karma)
    print("about:     ")
    buildHtml(user.about)
    print("submitted: ")
    println(user.submitted.length + " items")
  }

  override def BuildHelpUi(message: String): Unit = {
    print(message)
  }

  override def BuildErrorUi(message: String): Unit = {
    println(Console.RED + message + Console.RESET)
  }

  private def printBold(string: String): Unit = {
    print(Console.BOLD + string + Console.RESET)
  }

  private def buildStory(item: Item): Unit = {
    printBold("Story: " + item.title)
    println("(" + item.url + ")")
    buildStats(item)  }

  private def buildComment(item: Item): Unit = {
    printBold("Comment: ")
    buildHtml(item.text)
    buildStats(item)  }

  private def buildJob(item: Item): Unit = {
    printBold("Job: " + item.title)
    buildHtml(item.text)
    buildTime(item.time)
    buildStats(item)
  }

  private def buildPoll(item: Item): Unit = {
    printBold("Poll: " + item.title)
    buildStats(item)
  }

  private def buildStats(item: Item): Unit = {
    println("item id: " + item.id + ", " + item.score + " points, by " + item.by + " | " + item.descendants + " comments\n")
  }

  private def buildTime(time: Long): Unit = {
    println(if(time != -1) new Date(time * 1000) else "<unknown>")
  }

  private def buildHtml(text: String): Unit = {
    val htmlConverter = new HtmlConverter()
    htmlConverter.printHtml(text)
  }
}
