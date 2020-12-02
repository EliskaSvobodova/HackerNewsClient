package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import upickle.default.{read, write}

object ResponseRenderer {
  def fromItem(item: Item): String = {
    write[Item](item)
  }

  def fromUser(user: User): String = {
    write[User](user)
  }

  def fromArrayOfStoriesIds(stories: Array[Long]): String = {
    write(stories)
  }
}
