package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}
import upickle.implicits.key

case class Item (id: String = "",
                 deleted: Boolean = false,
                 @key("type") itemType: String = "unknown item type",
                 by: String = "unknown author",
                 time: Long = -1,
                 text: String = "no text",
                 dead: Boolean = false,
                 parent: String = "",
                 poll: Long = -1,
                 kids: Array[String] = Array(),
                 url: String = "unknown url",
                 score: Long = 0,
                 title: String = "no title",
                 parts: Array[String] = Array(),
                 descendants: Long = 0) {

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