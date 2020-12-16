package cz.cvut.fit.bioop.hackernewsclient.api.responseReaders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import upickle.default.read

class UPickleResponseReader extends ResponseReader {
  def toItem(response: String): Option[Item] =
    Option(read[Item](response))

  def toUser(response: String): Option[User] =
    Option(read[User](response))

  def toUpdates(response: String): Updates =
    read[Updates](response)

  def toArrayOfItemIds(response: String): Array[String] = {
    if (response == "[]")
      throw new IllegalArgumentException("Empty response")
    response.substring(1, response.length - 1).split(",")
  }
}
