package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

object ApiClient {
  def getItem(id: Long): Option[Item] = {
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/item/" + id + ".json?print=pretty")
    Option(ResponseParser.toItem(response))
  }

  def getUser(id: String): Option[User] = {
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/user/" + id + ".json")
    Option(ResponseParser.toUser(response))
  }

  def getTopStories(): Array[Long] = {
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json")
    ResponseParser.toArrayOfStoriesIds(topStoriesIds)
  }

  def getNewStories(): Array[Long] = {
    val newStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/newstories.json")
    ResponseParser.toArrayOfStoriesIds(newStoriesIds)
  }

  def getBestStories(): Array[Long] = {
    val bestStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/beststories.json")
    ResponseParser.toArrayOfStoriesIds(bestStoriesIds)
  }
}
