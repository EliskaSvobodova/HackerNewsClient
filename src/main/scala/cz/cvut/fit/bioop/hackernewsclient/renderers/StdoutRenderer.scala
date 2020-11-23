package cz.cvut.fit.bioop.hackernewsclient.renderers
import java.sql.Date

import cz.cvut.fit.bioop.hackernewsclient.Logger
import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}


class StdoutRenderer extends Renderer {
  private val logger = Logger(getClass.getSimpleName)

  override def renderItem(item: Item): Unit = {
    printBold(item.title)
    println("(" + item.url + ")")
    println(item.score + " points, by " + item.by + " | " + item.descendants + " comments")
  }

  override def renderUser(user: User): Unit = {
    print("user :     ")
    printBold(user.id + "\n")
    print("created:   ")
    println(if(user.created != -1) new Date(user.created * 1000) else "<unknown>")
    print("karma:     ")
    println(user.karma)
    print("about:     ")
    println(user.about)
    print("submitted: ")
    println(user.submitted.length + " items")
  }

  private def printBold(string: String): Unit = {
    print(Console.BOLD + string + Console.RESET)
  }
}
