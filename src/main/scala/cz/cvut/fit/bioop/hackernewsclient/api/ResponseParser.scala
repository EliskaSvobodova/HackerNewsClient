package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import upickle.default.read

object ResponseParser {
  def toItem(response: String): Option[Item] = {
    Option(read[Item](response))
  }

  def toUser(response: String): Option[User] = {
    Option(read[User](response))
  }

  def toUpdates(response: String): Updates = {
    read[Updates](response)
  }

  def toArrayOfStoriesIds(response: String): Array[Long] = {
    if(response == "[]")
      throw new IllegalArgumentException("Empty response")
    val storyIds = response.substring(1, response.length - 1).split(",")
    storyIds.map(id => id.toLong)
  }
}
