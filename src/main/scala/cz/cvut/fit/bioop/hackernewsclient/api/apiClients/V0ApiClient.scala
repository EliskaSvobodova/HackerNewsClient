package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.Logger
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.{ResponseReader, UPickleResponseReader}
import cz.cvut.fit.bioop.hackernewsclient.cache.Cache

import scala.io.Source

class V0ApiClient extends ApiClient {
  private val logger = Logger(getClass.getSimpleName)
  private val baseUrl: String = "https://hacker-news.firebaseio.com/v0/"

  override def getItem(id: Long): Option[Item] = {
    logger.info("Getting item from api client")
    val itemOpt = Cache.getItem(id)
    if(itemOpt.isDefined) {
      logger.info("Item was in cache")
      return itemOpt
    }
    logger.info("Item wasn't found in cache, getting it from web api")
    val response = get(baseUrl + "item/" + id + ".json")
    val itemFromApiOpt = ResponseReader.toItem(response)
    if(itemFromApiOpt.isDefined)
      Cache.cacheItem(itemFromApiOpt.get)
    itemFromApiOpt
  }

  override def getItems(ids: Array[Long]): Array[Item] = {
    logger.info("Getting items from api client")
    val items = Cache.getItems(ids)
    for((id, item) <- ids zip items)
      yield
        if(item.isEmpty) {
          logger.info("Item wasn't found in cache, getting it from web api")
          val itemOpt = ResponseReader.toItem(get(baseUrl + "item/" + id + ".json"))
          if(itemOpt.isDefined)
            Cache.cacheItem(itemOpt.get)
          itemOpt.get
        } else {
          logger.info("Item was in cache")
          item.get
        }
  }

  override def getUser(id: String): Option[User] = {
    val userOpt = Cache.getUser(id)
    if(userOpt.isDefined)
      return userOpt
    val response = get(baseUrl + "user/" + id + ".json")
    val userFromApiOpt = ResponseReader.toUser(response)
    if(userFromApiOpt.isDefined)
      Cache.cacheUser(userFromApiOpt.get)
    userFromApiOpt
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
