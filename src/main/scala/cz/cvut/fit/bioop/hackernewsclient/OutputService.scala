package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import scala.collection.mutable

/**
 * Puts together ApiClient and Renderer and performs lazy fetching
 */
object OutputService {
  /**
   * Fetches items from API one at a time according to given parameters
   * @param page page number
   * @param pageSize how many items page should contain
   * @param storiesIds array of all item ids, stories will be chosen from it according to page and pageSize
   */
  def displayPage(page: Int, pageSize: Int, storiesIds: Array[Long]): Unit = {
    if (page <= 0 || pageSize <= 0 || (page - 1) * pageSize > storiesIds.length) {
      throw new IllegalArgumentException("There are no more stories on page " + page + " " +
        "(page size = " + pageSize + ", number of stories available = " + storiesIds.length + ")")
    }
    val range = Range((page - 1) * pageSize, page * pageSize)
    for {
      (_, storyId) <- range zip storiesIds
    } yield {
      val itemOpt = ApiClient.getItem(storyId)
      if(itemOpt.isEmpty)
        throw new NoSuchElementException("Item with id " + storyId + " doesn't exist")
      Renderer.renderItem(itemOpt.get)
    }
  }

  /**
   * Displays user's information and then fetches user's items one at a time according to display parameter
   * @param userId user that should be displayed
   * @param display TreeSet of strings with item types that should be displayed (ex. "story", "comment")
   */
  def displayUser(userId: String, display: mutable.TreeSet[String]): Unit = {
    val userOpt = ApiClient.getUser(userId)
    if(userOpt.isEmpty){
      Renderer.displayError("User " + userId + " doesn't exist")
      throw new NoSuchElementException("User " + userId + " doesn't exist")
    }
    val user = userOpt.get
    Renderer.renderUser(user)
    if(display.nonEmpty)
      for(itemId <- user.submitted){
        val itemOpt = ApiClient.getItem(itemId)
        if(itemOpt.isEmpty)
          throw new NoSuchElementException("User's item with id " + itemId + " doesn't exist")
        val item = itemOpt.get
        if(!item.deleted && display.contains(item.itemType))
          Renderer.renderItem(item)
      }
  }
}
