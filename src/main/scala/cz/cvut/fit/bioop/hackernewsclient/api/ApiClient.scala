package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.cache.Cache
import cz.cvut.fit.bioop.hackernewsclient.commands.stories.{BestStoriesCommand, NewStoriesCommand, TopStoriesCommand}

object ApiClient {
  def getItem(id: Long): Option[Item] = {
    val itemOpt = Cache.getItem(id)
    if(itemOpt.isDefined)
      return itemOpt
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/item/" + id + ".json")
    ResponseParser.toItem(response)
  }

  def getUser(id: String): Option[User] = {
    val userOpt = Cache.getUser(id)
    if(userOpt.isDefined)
      return userOpt
    val response = RequestUrl.get("https://hacker-news.firebaseio.com/v0/user/" + id + ".json")
    ResponseParser.toUser(response)
  }

  def getTopStories(): Array[Long] = {
    val storiesOpt = Cache.getStories(TopStoriesCommand.name)
    if(storiesOpt.isDefined)
      return storiesOpt.get
    val topStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/topstories.json")
    ResponseParser.toArrayOfStoriesIds(topStoriesIds)
  }

  def getNewStories(): Array[Long] = {
    val storiesOpt = Cache.getStories(NewStoriesCommand.name)
    if(storiesOpt.isDefined)
      return storiesOpt.get
    val newStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/newstories.json")
    ResponseParser.toArrayOfStoriesIds(newStoriesIds)
  }

  def getBestStories(): Array[Long] = {
    val storiesOpt = Cache.getStories(BestStoriesCommand.name)
    if(storiesOpt.isDefined)
      return storiesOpt.get
    val bestStoriesIds = RequestUrl.get("https://hacker-news.firebaseio.com/v0/beststories.json")
    ResponseParser.toArrayOfStoriesIds(bestStoriesIds)
  }
}
