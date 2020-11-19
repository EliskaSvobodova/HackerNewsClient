package cz.cvut.fit.bioop.hackernewsclient.api

object ApiClient {
  def getItem(id: Long) = {
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/item/" + id + ".json?print=pretty")
    ResponseParser.toItem(response)
  }

  def getTopStories(): Array[Int] = {
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json")
    val storyIds = topStoriesIds.substring(1, topStoriesIds.length - 1).split(",")
    storyIds.map(id => id.toInt)
  }
}
