package cz.cvut.fit.bioop.hackernewsclient.cache
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

import scala.collection.mutable.ArrayBuffer

class MockCache(val itemsCache: ArrayBuffer[Item] = ArrayBuffer[Item](),
                val usersCache: ArrayBuffer[User] = ArrayBuffer[User]())
  extends Cache {

  override def getItem(id: String): Option[Item] = {
    itemsCache.find(item => item.id.toString == id)
  }

  override def getUser(id: String): Option[User] = {
    usersCache.find(user => user.id == id)
  }

  override def cacheItem(item: Item): Unit = {
    itemsCache += item
  }

  override def cacheUser(user: User): Unit = {
    usersCache += user
  }

  override def timeToLive: Long = 10

  override def clearCache(): Unit = {
    itemsCache.clear()
    usersCache.clear()
  }
}
