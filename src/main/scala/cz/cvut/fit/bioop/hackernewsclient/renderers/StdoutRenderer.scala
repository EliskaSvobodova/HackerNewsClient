package cz.cvut.fit.bioop.hackernewsclient.renderers
import java.sql.Date

import cz.cvut.fit.bioop.hackernewsclient.Logger
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}


class StdoutRenderer extends Renderer {
  private val logger = Logger(getClass.getSimpleName)

  override def renderItem(item: Item): Unit = {
    item.itemType match {
      case "comment" => renderComment(item)
      case "job" => renderJob(item)
      case "poll" => renderPoll(item)
      case _ => renderStory(item)
    }
  }

  override def renderUser(user: User): Unit = {
    print("user :     ")
    printBold(user.id + "\n")
    print("created:   ")
    renderTime(user.created)
    print("karma:     ")
    println(user.karma)
    print("about:     ")
    println(user.about)
    print("submitted: ")
    println(user.submitted.length + " items")
  }

  override def displayError(message: String): Unit = {
    println(Console.RED + message + Console.RESET)
  }

  private def printBold(string: String): Unit = {
    print(Console.BOLD + string + Console.RESET)
  }

  private def renderStory(item: Item): Unit = {
    printBold("Story: " + item.title)
    println("(" + item.url + ")")
    renderStats(item)  }

  private def renderComment(item: Item): Unit = {
    printBold("Comment: ")
    println(item.text)
    renderStats(item)  }

  private def renderJob(item: Item): Unit = {
    printBold("Job: " + item.title)
    println(item.text)
    renderTime(item.time)
    renderStats(item)
  }

  private def renderPoll(item: Item): Unit = {
    printBold("Poll: " + item.title)
    renderStats(item)
  }

  private def renderStats(item: Item): Unit = {
    println(item.score + " points, by " + item.by + " | " + item.descendants + " comments\n")
  }

  private def renderTime(time: Long): Unit = {
    println(if(time != -1) new Date(time * 1000) else "<unknown>")
  }
}
