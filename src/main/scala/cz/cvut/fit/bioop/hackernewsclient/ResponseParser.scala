package cz.cvut.fit.bioop.hackernewsclient
import cz.cvut.fit.bioop.hackernewsclient.apiObjects.Item
import upickle.default._


object ResponseParser {
  def getTitle(response: String): String = {
    val item = read[Item](response)
    item.title
  }
}
