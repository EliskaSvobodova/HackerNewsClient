package cz.cvut.fit.bioop.hackernewsclient.api.responseWriters

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}

/**
 * Transforms instances of data classes to string responses
 */
trait ResponseWriter {
  def fromItem(item: Item): String

  def fromUser(user: User): String

  def fromArrayOfItemIds(stories: Array[String]): String

  def fromUpdates(updates: Updates): String
}

object ResponseWriter extends ResponseWriter {
  val responseWriter: ResponseWriter = new UPickleResponseWriter()

  override def fromItem(item: Item): String =
    responseWriter.fromItem(item)

  override def fromUser(user: User): String =
    responseWriter.fromUser(user)

  override def fromArrayOfItemIds(stories: Array[String]): String =
    responseWriter.fromArrayOfItemIds(stories)

  override def fromUpdates(updates: Updates): String =
    responseWriter.fromUpdates(updates)
}