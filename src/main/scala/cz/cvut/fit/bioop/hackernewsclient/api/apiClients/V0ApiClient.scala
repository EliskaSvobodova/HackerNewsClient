package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.ApiRequest
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import cz.cvut.fit.bioop.hackernewsclient.cache.Cache
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger

/**
 * Actual implementation of ApiClient
 *
 * When asked for an element, it first checks the cache and if it isn't there,
 * ApiClient sends a request
 * @param apiRequest class responsible for fetching data from remote resource
 * @param cache local cache that caches elements and fetches them when asked
 */
class V0ApiClient(val apiRequest: ApiRequest, val cache: Cache) extends ApiClient {
  private val logger = Logger(getClass.getSimpleName)

  /**
   * Tries to fetch an Item according to specified id
   *
   * Caches the item, if it was found
   * @param id which item to fetch
   * @return item, if it was found (in cache or in request response) or None
   */
  override def getItem(id: String): Option[Item] = {
    val itemOpt = cache.getItem(id)
    if(itemOpt.isDefined) {
      logger.info("Item was in cache")
      return itemOpt
    }
    logger.info("Item wasn't found in cache, getting it from web api")
    val itemFromApiOpt = ResponseReader.toItem(apiRequest.getItem(id))
    if(itemFromApiOpt.isDefined)
      cache.cacheItem(itemFromApiOpt.get)
    itemFromApiOpt
  }

  /**
   * Tries to retrieve all items with ids from given array
   *
   * Caches all items, that was found
   * @return Array of items that was found
   */
  override def getItems(ids: Array[String]): Array[Item] = {
    for{
      id <- ids
      itemOpt = getItem(id)
      if itemOpt.isDefined
    } yield
        itemOpt.get
  }

  /**
   * Tries to fetch a User according to specified id
   *
   * Caches the user, if it was found
   * @param id which user to fetch
   * @return user, if it was found (in cache or in request response) or None
   */
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

  /**
   * Returns array of ids of top stories. Doesn't use cache. Always fetches a fresh list.
   */
  override def getTopStories: Array[String] = {
    ResponseReader.toArrayOfItemIds(apiRequest.getTopStories)
  }

  /**
   * Returns array of ids of new stories. Doesn't use cache. Always fetches a fresh list.
   */
  override def getNewStories: Array[String] = {
    ResponseReader.toArrayOfItemIds(apiRequest.getNewStories)
  }

  /**
   * Returns array of ids of best stories. Doesn't use cache. Always fetches a fresh list.
   */
  override def getBestStories: Array[String] = {
    ResponseReader.toArrayOfItemIds(apiRequest.getBestStories)
  }

  /**
   * Returns instance of Updates class, that holds ids of updated user and items.
   * Doesn't use cache. Always fetches fresh data.
   */
  override def getUpdates: Updates = {
    ResponseReader.toUpdates(apiRequest.getUpdates)
  }
}
