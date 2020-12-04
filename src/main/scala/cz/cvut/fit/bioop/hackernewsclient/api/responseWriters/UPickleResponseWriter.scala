package cz.cvut.fit.bioop.hackernewsclient.api.responseWriters

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import upickle.default.write

class UPickleResponseWriter extends ResponseWriter {
  def fromItem(item: Item): String =
    write[Item](item)

  def fromUser(user: User): String =
    write[User](user)

  def fromArrayOfItemIds(stories: Array[Long]): String =
    write(stories)
}
