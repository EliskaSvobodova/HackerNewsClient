package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

object ApiClient {
  def getItem(id: Long): Item = {
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/item/" + id + ".json?print=pretty")
    ResponseParser.toItem(response)
  }

  def getUser(id: String): User = {
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/user/" + id + ".json")
    ResponseParser.toUser(response)
  }

  def getTopStories(): Array[Int] = {
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json")
    ResponseParser.toArrayOfStoriesIds(topStoriesIds)
  }

  def getNewStories(): Array[Int] = {
    val newStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/newstories.json")
    ResponseParser.toArrayOfStoriesIds(newStoriesIds)
  }

  def getBestStories(): Array[Int] = {
    val bestStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/beststories.json")
    ResponseParser.toArrayOfStoriesIds(bestStoriesIds)
  }
}
