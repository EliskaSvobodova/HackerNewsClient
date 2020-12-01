package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.ResponseParser
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

import scala.io.Source

object Cache {
  def getItem(id: Long): Option[Item] = {
    if(getClass.getClassLoader.getResource("cachefiles/items") != null){
      val r = ("^" + id + "(.*)").r
      val items = Source.fromResource("cachefiles/items").getLines
      for(item <- items) {
        item match{
          case r(rest) => return ResponseParser.toItem(rest)
          case _ =>
        }
      }
    }
    None
  }

  def getUser(id: String): Option[User] = {
    if(getClass.getClassLoader.getResource("cachefiles/users") != null){
      val r = ("^" + id + "({.*)").r
      val users = Source.fromResource("cachefiles/users").getLines
      for(user <- users) {
        user match{
          case r(rest) => return ResponseParser.toUser(rest)
          case _ =>
        }
      }
    }
    None
  }

  def getStories(name: String): Option[Array[Long]] = {
    if(getClass.getClassLoader.getResource("cachefiles/stories") != null){
      val r = ("^" + name + "(.*)").r
      val allStories = Source.fromResource("cachefiles/stories").getLines
      for(stories <- allStories) {
        stories match{
          case r(rest) => return Option(ResponseParser.toArrayOfStoriesIds(rest))
          case _ =>
        }
      }
    }
    None
  }
}
