package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.{ResponseReader, UPickleResponseReader}
import cz.cvut.fit.bioop.hackernewsclient.cache.Cache

import scala.io.Source

class V0ApiClient extends ApiClient {
  private val baseUrl: String = "https://hacker-news.firebaseio.com/v0/"

  override def getItem(id: Long): Option[Item] = {
    val itemOpt = Cache.getItem(id)
    if(itemOpt.isDefined)
      return itemOpt
    val response = get(baseUrl + "item/" + id + ".json")
    ResponseReader.toItem(response)
  }

  override def getUser(id: String): Option[User] = {
    val userOpt = Cache.getUser(id)
    if(userOpt.isDefined)
      return userOpt
    val response = get(baseUrl + "user/" + id + ".json")
    ResponseReader.toUser(response)
  }

  override def getTopStories: Array[Long] = {
    val topStoriesIds = get(baseUrl + "topstories.json")
    ResponseReader.toArrayOfItemIds(topStoriesIds)
  }

  override def getNewStories: Array[Long] = {
    val newStoriesIds = get(baseUrl + "newstories.json")
    ResponseReader.toArrayOfItemIds(newStoriesIds)
  }

  override def getBestStories: Array[Long] = {
    val bestStoriesIds = get(baseUrl + "beststories.json")
    ResponseReader.toArrayOfItemIds(bestStoriesIds)
  }

  override def getUpdates: Updates = {
    ResponseReader.toUpdates(get(baseUrl + "updates.json"))
  }

  private def get(url: String) = {
    val source = Source.fromURL(url)
    val content = source.mkString
    source.close()
    content
  }
}
