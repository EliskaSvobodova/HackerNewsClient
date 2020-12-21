package cz.cvut.fit.bioop.hackernewsclient.api.apiRequests

import scala.io.Source

/**
 * Sends requests to hacker-news web api and returns results
 */
class HackerNewsApiRequest extends ApiRequest {
  private val baseUrl: String = "https://hacker-news.firebaseio.com/v0/"

  /**
   * Sends request to specified url
   */
  private def get(url: String) = {
    val source = Source.fromURL(url)
    val content = source.mkString
    source.close()
    content
  }

  /**
   * Ask for item with specified id
   */
  override def getItem(id: String): String = get(baseUrl + "item/" + id + ".json")

  /**
   * Ask for user with specified id
   */
  override def getUser(id: String): String = get(baseUrl + "user/" + id + ".json")

  /**
   * Ask for top stories
   */
  override def getTopStories: String = get(baseUrl + "topstories.json")

  /**
   * Ask for new stories
   */
  override def getNewStories: String = get(baseUrl + "newstories.json")

  /**
   * Ask for best stories
   */
  override def getBestStories: String = get(baseUrl + "beststories.json")

  /**
   * Ask for updates
   */
  override def getUpdates: String = get(baseUrl + "updates.json")
}
