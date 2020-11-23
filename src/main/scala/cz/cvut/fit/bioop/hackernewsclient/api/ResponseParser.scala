package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import upickle.default.read

object ResponseParser {
  def toItem(response: String): Item = {
    read[Item](response)
  }

  def toUser(response: String): User = {
    read[User](response)
  }

  def toArrayOfStoriesIds(response: String): Array[Int] = {
    val storyIds = response.substring(1, response.length - 1).split(",")
    storyIds.map(id => id.toInt)
  }
}
