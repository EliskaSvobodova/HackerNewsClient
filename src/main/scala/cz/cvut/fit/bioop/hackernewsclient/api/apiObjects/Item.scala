package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}

case class Item (id: Long = -1,
                 deleted: Boolean = false,
                 itemType: String = "unknown item type",
                 by: String = "unknown author",
                 time: Long = -1,
                 text: String = "no text",
                 dead: Boolean = false,
                 parent: Long = -1,
                 poll: Long = -1,
                 kids: Array[Long] = Array(),
                 url: String = "unknown url",
                 score: Long = 0,
                 title: String = "no title",
                 parts: Array[Long] = Array(),
                 descendants: Long = -1) {

  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("Item[", ", ", "]")
  }
}

object Item{
  implicit val rw: RW[Item] = macroRW
}