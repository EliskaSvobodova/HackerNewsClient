package cz.cvut.fit.bioop.hackernewsclient.cache
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

import scala.collection.mutable.ArrayBuffer

class MockCache extends Cache {
  private val itemsCache = ArrayBuffer[Item]()
  private val usersCache = ArrayBuffer[User]()

  override def getItem(id: Long): Option[Item] = {
    itemsCache.find(item => item.id == id)
  }

  override def getUser(id: String): Option[User] = None

  override def cacheItem(item: Item): Unit = {
    itemsCache += item
  }

  override def cacheUser(user: User): Unit = {
    usersCache += user
  }

  override def timeToLive: Long = 10
}
