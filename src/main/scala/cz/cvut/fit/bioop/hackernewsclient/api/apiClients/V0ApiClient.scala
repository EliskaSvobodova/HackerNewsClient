package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.ApiRequest
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import cz.cvut.fit.bioop.hackernewsclient.cache.Cache
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger

class V0ApiClient(apiRequest: ApiRequest, cache: Cache) extends ApiClient {
  private val logger = Logger(getClass.getSimpleName)

  override def getItem(id: Long): Option[Item] = {
    val itemOpt = cache.getItem(id)
    if(itemOpt.isDefined) {
      logger.info("Item was in cache")
      return itemOpt
    }
    logger.info("Item wasn't found in cache, getting it from web api")
    val response = apiRequest.getItem(id)
    val itemFromApiOpt = ResponseReader.toItem(response)
    if(itemFromApiOpt.isDefined)
      cache.cacheItem(itemFromApiOpt.get)
    itemFromApiOpt
  }

  override def getItems(ids: Array[Long]): Array[Item] = {
    logger.info("Getting items from api client")
    for(id <- ids)
      yield {
        val itemCacheOpt = cache.getItem(id)
        if(itemCacheOpt.isDefined) {
          logger.info("Item was in cache")
          itemCacheOpt.get
        } else {
          logger.info("Item wasn't found in cache, getting it from web api")
          val itemApiOpt = ResponseReader.toItem(apiRequest.getItem(id))
          if(itemApiOpt.isDefined)
            cache.cacheItem(itemApiOpt.get)
          itemApiOpt.get
        }
      }
  }

  override def getUser(id: String): Option[User] = {
    val userOpt = cache.getUser(id)
    if(userOpt.isDefined)
      return userOpt
    val response = apiRequest.getUser(id)
    val userFromApiOpt = ResponseReader.toUser(response)
    if(userFromApiOpt.isDefined)
      cache.cacheUser(userFromApiOpt.get)
    userFromApiOpt
  }

  override def getTopStories: Array[Long] = {
    ResponseReader.toArrayOfItemIds(apiRequest.getTopStories)
  }

  override def getNewStories: Array[Long] = {
    ResponseReader.toArrayOfItemIds(apiRequest.getNewStories)
  }

  override def getBestStories: Array[Long] = {
    ResponseReader.toArrayOfItemIds(apiRequest.getBestStories)
  }

  override def getUpdates: Updates = {
    ResponseReader.toUpdates(apiRequest.getUpdates)
  }
}
