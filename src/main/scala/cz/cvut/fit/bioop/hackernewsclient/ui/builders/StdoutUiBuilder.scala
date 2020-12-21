package cz.cvut.fit.bioop.hackernewsclient.ui.builders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.ui.html.HtmlConverter

import java.sql.Date

/**
 * Constructs strings to be displayed to the user in UI
 */
class StdoutUiBuilder extends UiBuilder[String] {
  private val htmlConverter = new HtmlConverter()

  override def buildItemUi(item: Item): String = {
    item.itemType match {
      case "comment" => buildComment(item)
      case "job" => buildJob(item)
      case "poll" => buildPoll(item)
      case "pollopt" => buildPollopt(item)
      case _ => buildStory(item)
    }
  }

  override def buildUserUi(user: User): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder
      .append("user :     " + buildBold(user.id) + "\n")
      .append("created:   " + buildTime(user.created) + "\n")
      .append("karma:     " + user.karma + "\n")
      .append("about:     " + htmlConverter.convertHtml(user.about) + "\n")
      .append("submitted: " + user.submitted.length + " items" + "\n")
    stringBuilder.toString()
  }

  override def buildHelpUi(message: String): String = message

  override def buildErrorUi(message: String): String = "\\e[0;31m" + message + "\\e[m"

  private def buildBold(string: String): String = htmlConverter.convertHtml("<b>" + string + "</b>")

  private def buildStory(item: Item): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder
      .append(buildBold("Story: " + item.title) + "(" + item.url + ")" + "\n")
      .append(buildStats(item))
    stringBuilder.toString()
  }

  private def buildComment(item: Item): String =
    buildBold("Comment: ") + htmlConverter.convertHtml(item.text) + "\n" + buildStats(item)

  private def buildJob(item: Item): String = {
    val stringBuilder = new StringBuilder()
    stringBuilder
      .append(buildBold("Job: " + item.title) + " " + buildTime(item.time) + "\n")
      .append(htmlConverter.convertHtml(item.text) + "\n")
      .append(buildStats(item))
    stringBuilder.toString()
  }

  private def buildPoll(item: Item): String = {
    buildBold("Poll: " + item.title) + "\n" +
      "parts: " + item.parts.mkString(",") + "\n" +
      buildStats(item)
  }

  private def buildPollopt(item: Item): String = {
    buildBold("Pollopt:") + "\n" +
      htmlConverter.convertHtml(item.text) + "\n" +
      buildStats(item)
  }

  private def buildStats(item: Item): String = {
    "item id: " + item.id + ", " + item.score + " points, by " + item.by + " | " + item.descendants + " comments\n"
  }

  private def buildTime(time: Long): String = {
    if(time != -1)
      new Date(time * 1000).toString
    else
      "<unknown>"
  }
}
