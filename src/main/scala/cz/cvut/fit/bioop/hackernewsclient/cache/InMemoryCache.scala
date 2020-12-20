package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import cz.cvut.fit.bioop.hackernewsclient.api.responseWriters.ResponseWriter
import cz.cvut.fit.bioop.hackernewsclient.cache.locations.{CacheLocation, FileLocation}

import java.util.Calendar
import scala.collection.mutable.ArrayBuffer

class InMemoryCache(override val timeToLive: Long = Cache.defaultTimeToLive,
                    private val itemsCache: CacheLocation = new FileLocation("items"),
                    private val usersCache: CacheLocation = new FileLocation("users"))
  extends Cache {

  private val divider = "~"
  private val storePattern = ("([^" + divider + "]+)" + divider + "([0-9]+)" + divider + "(.+)").r

  override def getItem(itemId: String): Option[Item] = {
    val recordOpt = getRecord(itemId, itemsCache)
    if(recordOpt.isDefined)
      ResponseReader.toItem(recordOpt.get)
    else
      None
  }

  override def getUser(userId: String): Option[User] = {
    val recordOpt = getRecord(userId, usersCache)
    if(recordOpt.isDefined)
      ResponseReader.toUser(recordOpt.get)
    else
      None
  }

  private def getRecord(id: String, location: CacheLocation): Option[String] ={
    val lines = location.getLines
    for(line <- lines){
      val storePattern(cachedId, _, rest) = line
      if(cachedId == id) {
        return Option(rest)
      }
    }
    None
  }

  override def cacheItem(item: Item): Unit = {
    cacheElement(item, item.id.toString, itemsCache)
  }

  override def cacheUser(user: User): Unit = {
    cacheElement(user, user.id, usersCache)
  }

  implicit val itemToCacheable: Cacheable[Item] =
    (item: Item) => item.id + divider + Calendar.getInstance().getTime.getTime + divider + ResponseWriter.fromItem(item)

  implicit val userToCacheable: Cacheable[User] =
    (user: User) => user.id + divider + Calendar.getInstance().getTime.getTime + divider + ResponseWriter.fromUser(user)

  private def cacheElement[T](x: T, id: String, location: CacheLocation)(implicit elem: Cacheable[T]): Unit = {
    if(isElemCachedAndUpdate(id, location)) {
      return
    }
    location.append(elem.toCacheable(x))
  }

  private def isElemCachedAndUpdate(id: String, location: CacheLocation): Boolean = {
    val updated = ArrayBuffer[String]()
    val lines = location.getLines
    var isElemCached = false

    val minAge = Calendar.getInstance().getTime.getTime - timeToLive * 1000
    for(line <- lines){
      val storePattern(cachedId, age, _) = line
      if(cachedId == id) {  // element is already cached
        isElemCached = true
      }
      if(age.toLong > minAge)
        updated.append(line)
    }

    location.write(updated)
    isElemCached
  }

  override def clearCache(): Unit = {
    itemsCache.clearCache()
    usersCache.clearCache()
  }
}
