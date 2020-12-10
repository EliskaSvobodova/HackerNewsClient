package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

trait Cache {
  def getItem(id: Long): Option[Item]

  def getItems(ids: Array[Long]): Array[Option[Item]]

  def getUser(id: String): Option[User]

  def cacheItem(item: Item): Unit

  def cacheItems(items: Array[Item]): Unit

  def cacheUser(user: User): Unit
}

object Cache extends Cache {
  val cache: Cache = new InMemoryCache()

  override def getItem(id: Long): Option[Item] =
    cache.getItem(id)

  override def getItems(ids: Array[Long]): Array[Option[Item]] =
    cache.getItems(ids)

  override def getUser(id: String): Option[User] =
    cache.getUser(id)

  override def cacheItem(item: Item): Unit =
    cache.cacheItem(item)

  override def cacheItems(items: Array[Item]): Unit =
    cache.cacheItems(items)

  override def cacheUser(user: User): Unit =
    cache.cacheUser(user)
}
