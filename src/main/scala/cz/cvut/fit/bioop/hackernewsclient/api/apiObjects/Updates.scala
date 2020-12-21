package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}

/**
 * Data class that holds info from web API about recently changed objects
 * @param items Ids of items that was changed
 * @param profiles Ids of users that was changed
 */
case class Updates(items: Array[Long], profiles: Array[String]) {
  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("Updates[", ", ", "]")
  }

  def sameUpdates(other: Updates): Boolean = {
    items.sameElements(other.items) && profiles.sameElements(other.profiles)
  }
}

object Updates{
  implicit val rw: RW[Updates] = macroRW
}