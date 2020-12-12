package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import cz.cvut.fit.bioop.hackernewsclient.api.responseWriters.ResponseWriter

import java.io.{File, FileWriter}
import scala.io.Source

class InMemoryCache extends Cache {
  private val cachePath = "cache/"
  private val itemsFile = "items"
  private val usersFile = "users"

  private val divider = "~"
  private val storePattern = ("([^" + divider + "]+)" + divider + "(.+)").r

  override def getItem(itemId: Long): Option[Item] = {
    if(ApiClient.getUpdates.items.contains(itemId)) {
      return None
    }
    val recordOpt = getRecord(itemId.toString, itemsFile)
    if(recordOpt.isDefined)
      ResponseReader.toItem(recordOpt.get)
    else
      None
  }

  override def getUser(userId: String): Option[User] = {
    if(ApiClient.getUpdates.profiles.contains(userId))
      return None
    val recordOpt = getRecord(userId, usersFile)
    if(recordOpt.isDefined)
      ResponseReader.toUser(recordOpt.get)
    else
      None
  }

  private def getRecord(id: String, fileName: String): Option[String] ={
    val source = getSource(fileName)
    val lines = source.getLines()
    for(line <- lines){
      val storePattern(cachedId, rest) = line
      if(cachedId == id) {
        source.close()
        return Option(rest)
      }
    }
    source.close()
    None
  }

  override def cacheItem(item: Item): Unit = {
    cacheElement(item, item.id.toString, itemsFile)
  }

  override def cacheUser(user: User): Unit = {
    cacheElement(user, user.id, usersFile)
  }

  implicit val itemToCacheable: Cacheable[Item] = (item: Item) => item.id + divider + ResponseWriter.fromItem(item)

  implicit val userToCacheable: Cacheable[User] = (user: User) => user.id + divider + ResponseWriter.fromUser(user)

  private def cacheElement[T](x: T, id: String, fileName: String)(implicit elem: Cacheable[T]): Unit = {
    if(isElementCached(id, fileName)) {
      return
    }
    val writer = new FileWriter(getFile(fileName), true)
    writer.write(elem.toCacheable(x) + "\n")
    writer.close()
  }

  private def isElementCached(id: String, fileName: String): Boolean = {
    val source = getSource(fileName)
    val lines = source.getLines()
    for(line <- lines){
      val storePattern(cachedId, _) = line
      if(cachedId == id) {  // element is already cached
        source.close()
        return true
      }
    }
    source.close()
    false
  }

  private def getSource(filename: String): Source = {
    Source.fromFile(getFile(filename))
  }

  private def getFile(filename: String): File = {
    val directory = new File(cachePath)
    directory.mkdir()  // create cache directory if it doesn't exist
    val cacheFile = new File(cachePath + filename)
    if(!cacheFile.exists())
      cacheFile.createNewFile()
    cacheFile
  }
}
