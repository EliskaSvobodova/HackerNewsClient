package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.Logger
import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import cz.cvut.fit.bioop.hackernewsclient.api.responseWriters.ResponseWriter

import java.io.{File, FileWriter}
import scala.io.Source

class InMemoryCache extends Cache {
  private val logger = Logger(getClass.getSimpleName)

  private val cachePath = "cache/"
  private val itemsFile = "items"
  private val usersFile = "users"

  private val divider = "~"
  private val storePattern = ("([^" + divider + "]+)" + divider + "(.+)").r

  override def getItem(itemId: Long): Option[Item] = {
    if(ApiClient.getUpdates.items.contains(itemId)) {
      logger.info("Item was updated, it needs to be loaded from web api")
      return None
    }
    val source = getSource(itemsFile)
    logger.info("Opened cache file, starting search for item id")
    val lines = source.getLines()
    for(line <- lines){
      val storePattern(id, rest) = line
      if(itemId == id.toLong) {
        source.close()
        return ResponseReader.toItem(rest)
      }
    }
    source.close()
    None
  }

  override def getItems(ids: Array[Long]): Array[Option[Item]] = {
    for(itemId <- ids) yield getItem(itemId)
  }

  override def getUser(userId: String): Option[User] = {
    if(ApiClient.getUpdates.profiles.contains(userId))
      return None
    val source = getSource(usersFile)
    val lines = source.getLines()
    for(line <- lines){
      val storePattern(id, rest) = line
      if(userId == id) {
        source.close()
        return ResponseReader.toUser(rest)
      }
    }
    source.close()
    None
  }

  override def cacheItem(item: Item): Unit = {
    logger.info("caching item " + item)
    if(isItemCached(item)) {
      logger.info("cacheItem - item is already cached")
      return
    }
    val writer = new FileWriter(getFile(itemsFile), true)
    writeItem(item, writer)
    writer.close()
  }

  override def cacheItems(items: Array[Item]): Unit = {
    val source = getSource(itemsFile)
    val lines = source.getLines()
    val itemCached = Array.fill(items.length)(false)
    for(line <- lines){
      val storePattern(id, _) = line
      val index = items.indexWhere(item => item.id == id.toLong)
      if(index != -1)  // item is already cached
        itemCached(index) = true
    }
    source.close()

    val writer = new FileWriter(getFile(itemsFile), true)
    for((itCached, idx) <- itemCached.zipWithIndex){
      if(!itCached)
        writeItem(items(idx), writer)
    }
    writer.close()
  }

  override def cacheUser(user: User): Unit = {
    if(isUserCached(user))
      return
    val writer = new FileWriter(getFile(usersFile), true)
    writeUser(user, writer)
    writer.close()
  }

  private def writeItem(item: Item, writer: FileWriter): Unit = {
    logger.info("caching item: " + item.id + divider + ResponseWriter.fromItem(item))
    writer.write(item.id + divider + ResponseWriter.fromItem(item) + "\n")
  }

  private def writeUser(user: User, writer: FileWriter): Unit = {
    writer.write(user.id + divider + ResponseWriter.fromUser(user) + "\n")
  }

  private def isItemCached(item: Item): Boolean = {
    val source = getSource(itemsFile)
    val lines = source.getLines()
    for(line <- lines){
      val storePattern(id, _) = line
      if(item.id == id.toLong) {  // item is already cached
        source.close()
        return true
      }
    }
    source.close()
    false
  }

  private def isUserCached(user: User): Boolean = {
    val source = getSource(usersFile)
    val lines = source.getLines()
    for(line <- lines){
      val storePattern(id, _) = line
      if(user.id == id) { // user is already cached
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
