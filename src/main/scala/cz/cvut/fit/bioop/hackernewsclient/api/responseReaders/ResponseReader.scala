package cz.cvut.fit.bioop.hackernewsclient.api.responseReaders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}

trait ResponseReader {
  def toItem(response: String): Option[Item]

  def toUser(response: String): Option[User]

  def toUpdates(response: String): Updates

  def toArrayOfItemIds(response: String): Array[Long]
}

object ResponseReader extends ResponseReader {
  val responseReader: ResponseReader = new UPickleResponseReader()

  def toItem(response: String): Option[Item] =
    responseReader.toItem(response)

  def toUser(response: String): Option[User] =
    responseReader.toUser(response)

  def toUpdates(response: String): Updates =
    responseReader.toUpdates(response)

  def toArrayOfItemIds(response: String): Array[Long] =
    responseReader.toArrayOfItemIds(response)
}