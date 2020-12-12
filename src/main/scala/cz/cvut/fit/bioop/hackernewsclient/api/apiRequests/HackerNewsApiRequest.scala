package cz.cvut.fit.bioop.hackernewsclient.api.apiRequests

import scala.io.Source

class HackerNewsApiRequest extends ApiRequest {
  private val baseUrl: String = "https://hacker-news.firebaseio.com/v0/"

  private def get(url: String) = {
    val source = Source.fromURL(url)
    val content = source.mkString
    source.close()
    content
  }

  override def getItem(id: Long): String = get(baseUrl + "item/" + id + ".json")

  override def getUser(id: String): String = get(baseUrl + "user/" + id + ".json")

  override def getTopStories: String = get(baseUrl + "topstories.json")

  override def getNewStories: String = get(baseUrl + "newstories.json")

  override def getBestStories: String = get(baseUrl + "beststories.json")

  override def getUpdates: String = get(baseUrl + "updates.json")
}
