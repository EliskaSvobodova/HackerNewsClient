package cz.cvut.fit.bioop.hackernewsclient.services

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import java.util.NoSuchElementException
import scala.collection.mutable.ArrayBuffer

object ItemService {
  /**
   * Fetches items from API one at a time according to given parameters
   * @param page page number
   * @param pageSize how many items page should contain
   * @param itemsIds array of all item ids, stories will be chosen from it according to page and pageSize
   */
  def displayPage(page: Int, pageSize: Int, itemsIds: Array[Long]): Array[Item] = {
    if (page <= 0 || pageSize <= 0 || (page - 1) * pageSize > itemsIds.length) {
      throw new IllegalArgumentException("There are no more items on page " + page + " " +
        "(page size = " + pageSize + ", number of stories available = " + itemsIds.length + ")")
    }
    val displayedItems = new ArrayBuffer[Item]()
    for (id <- itemsIds.slice((page - 1) * pageSize, page * pageSize)) {
      try {
        val item = displayItem(id)
        displayedItems += item
      } catch {
        case e: NoSuchElementException => // skip
      }
    }
    displayedItems.toArray
  }

  def displayItem(id: Long): Item = {
    val itemOpt = ApiClient.getItem(id)
    if(itemOpt.isEmpty)
      throw new NoSuchElementException("Item with id " + id + " doesn't exist!")
    Renderer.renderItem(itemOpt.get)
    itemOpt.get
  }
}
