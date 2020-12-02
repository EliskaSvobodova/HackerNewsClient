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
    for (storyId <- storiesIds.slice((page - 1) * pageSize, page * pageSize)) {
      val itemOpt = ApiClient.getItem(storyId)
      if(itemOpt.isDefined)
        Renderer.renderItem(itemOpt.get)
    }
  }

  def displayItemWithComments(itemId: Long, page: Int, pageSize: Int): Unit = {
    val itemOpt = ApiClient.getItem(itemId)
    if(itemOpt.isEmpty){
      throw new NoSuchElementException("Item " + itemId + " doesn't exist")
    }
    val item = itemOpt.get
    Renderer.renderItem(item)

    if (page <= 0 || pageSize <= 0 || (page - 1) * pageSize > item.kids.length) {
      throw new IllegalArgumentException("There are no more comments on page " + page + " " +
        "(page size = " + pageSize + ", number of comments available = " + item.kids.length + ")")
    }

    for(commentId <- item.kids.slice((page - 1) * pageSize, page * pageSize)) {
      val commentOpt = ApiClient.getItem(commentId)
      if(commentOpt.isDefined && !commentOpt.get.deleted)
        Renderer.renderItem(commentOpt.get)
    }
  }
}
