package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.{ResponseReader, UPickleResponseReader}
import cz.cvut.fit.bioop.hackernewsclient.api.responseWriters.{ResponseWriter, UPickleResponseWriter}

import java.io.{File, FileWriter}
import scala.collection.mutable
import scala.io.Source

object Cache {
  private val itemsFile = "cachefiles/items"
  private val usersFile = "cachefiles/users"

  private val divider = '~'
  private val storePattern = ("([^~]+)" + divider + "(.+)").r

  def getItem(id: Long): Option[Item] = {
    if(ApiClient.getUpdates.items.contains(id))
      return None
    if(getClass.getClassLoader.getResource(itemsFile) != null){
      val r = ("^" + id + divider + "(.*)").r
      val items = Source.fromResource(itemsFile).getLines
      for(item <- items) {
        item match{
          case r(rest) => return ResponseReader.toItem(rest)
          case _ =>
        }
      }
    }
    None
  }

  def getUser(id: String): Option[User] = {
    if(getClass.getClassLoader.getResource(usersFile) != null){
      val r = ("^" + id + divider + "({.*)").r
      val users = Source.fromResource(usersFile).getLines
      for(user <- users) {
        user match{
          case r(rest) => return ResponseReader.toUser(rest)
          case _ =>
        }
      }
    }
    None
  }

  def cacheItems(items: Array[Item]): Unit = {
    if(getClass.getClassLoader.getResource(itemsFile) == null){
      // create new cache file with new items
      val writer = new FileWriter(new File(itemsFile ))
      val itemsToBeCached = for(item <- items) yield item.id + divider + ResponseWriter.fromItem(item)
      writeToCacheFile(itemsToBeCached, writer)
      writer.close()
    } else {
      val itemsToBeCached = removeDuplicates(items)

      val fileWriter = new FileWriter(new File(itemsFile), true)
      writeToCacheFile(itemsToBeCached.map(item => item.id + divider + ResponseWriter.fromItem(item)), fileWriter)
      fileWriter.close()
    }
  }

  private def removeDuplicates(items: Array[Item]): Array[Item] = {
    val fileSource = Source.fromFile(itemsFile)
    val lines = fileSource.getLines()
    val itemsToBeCached = mutable.Map(items.map(item => (item.id, item)): _*)
    for(line <- lines){
      val storePattern(id, _) = line
      itemsToBeCached.remove(id.toLong)
    }
    fileSource.close()

    itemsToBeCached.values.toArray
  }

  private def writeToCacheFile(elements: Array[String], fileWriter: FileWriter): Unit = {
    for(elem <- elements){
      fileWriter.write(elem + "\n")
    }
  }
}
