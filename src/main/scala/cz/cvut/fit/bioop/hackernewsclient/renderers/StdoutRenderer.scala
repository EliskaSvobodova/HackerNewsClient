package cz.cvut.fit.bioop.hackernewsclient.renderers
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item


class StdoutRenderer extends Renderer {
  override def renderItem(item: Item): Unit = {
    printBold(item.title)
    println("(" + item.url + ")")
    println(item.score + " points, by " + item.by + " | " + item.descendants + " comments")
  }

  private def printBold(string: String): Unit = {
    print(Console.BOLD + string + Console.RESET)
  }
}
