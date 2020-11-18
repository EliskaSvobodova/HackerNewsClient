package cz.cvut.fit.bioop.hackernewsclient.apiObjects
import upickle.default.{ReadWriter => RW, macroRW}

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
                 descendants: Long = -1)

object Item{
  implicit val rw: RW[Item] = macroRW
}