package cz.cvut.fit.bioop.hackernewsclient.api.responseWriters

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

trait ResponseWriter {
  def fromItem(item: Item): String

  def fromUser(user: User): String

  def fromArrayOfItemIds(stories: Array[Long]): String
}

object ResponseWriter extends ResponseWriter {
  val responseWriter: ResponseWriter = new UPickleResponseWriter()

  def fromItem(item: Item): String =
    responseWriter.fromItem(item)

  def fromUser(user: User): String =
    responseWriter.fromUser(user)

  def fromArrayOfItemIds(stories: Array[Long]): String =
    responseWriter.fromArrayOfItemIds(stories)
}