package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

trait Cache {
  def getItem(id: Long): Option[Item]

  def getUser(id: String): Option[User]

  def cacheItem(item: Item): Unit

  def cacheUser(user: User): Unit
}

object Cache extends Cache {
  val cache: Cache = new InMemoryCache()

  override def getItem(id: Long): Option[Item] =
    cache.getItem(id)

  override def getUser(id: String): Option[User] =
    cache.getUser(id)

  override def cacheItem(item: Item): Unit =
    cache.cacheItem(item)

  override def cacheUser(user: User): Unit =
    cache.cacheUser(user)
}
