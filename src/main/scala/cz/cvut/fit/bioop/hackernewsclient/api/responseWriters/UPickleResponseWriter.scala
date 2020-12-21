package cz.cvut.fit.bioop.hackernewsclient.api.responseWriters

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import upickle.default.write

/**
* Response writer that uses UPickle library to wrap up api objects
*/
class UPickleResponseWriter extends ResponseWriter {
  override def fromItem(item: Item): String =
    write[Item](item)

  override def fromUser(user: User): String =
    write[User](user)

  override def fromArrayOfItemIds(stories: Array[String]): String =
    write(stories)

  override def fromUpdates(updates: Updates): String =
    write[Updates](updates)
}
