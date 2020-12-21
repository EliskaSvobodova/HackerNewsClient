package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}
import upickle.implicits.key

/**
 * Data class that holds info about items from web api
 * @param id The item's unique id.
 * @param deleted True if the item is deleted.
 * @param itemType The type of item. One of "job", "story", "comment", "poll", or "pollopt".
 * @param by The username of the item's author.
 * @param time Creation date of the item, in Unix Time.
 * @param text The comment, story or poll text. HTML.
 * @param dead True if the item is dead.
 * @param parent The comment's parent: either another comment or the relevant story.
 * @param poll The pollopt's associated poll.
 * @param kids The ids of the item's comments, in ranked display order.
 * @param url The URL of the story.
 * @param score The story's score, or the votes for a pollopt.
 * @param title The title of the story, poll or job. HTML.
 * @param parts A list of related pollopts, in display order.
 * @param descendants In the case of stories or polls, the total comment count.
 */
case class Item (id: Long = -1,
                 deleted: Boolean = false,
                 @key("type") itemType: String = "unknown item type",
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