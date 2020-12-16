package cz.cvut.fit.bioop.hackernewsclient.services

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.{ApiClient, ApiClientFactory}
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import java.util.NoSuchElementException
import scala.collection.mutable.ArrayBuffer

class ItemService(val apiClient: ApiClient = ApiClientFactory()) {
  def display(id: String): Item = {
    val itemOpt = apiClient.getItem(id)
    if(itemOpt.isEmpty)
      throw new NoSuchElementException("Item with id " + id + " doesn't exist!")
    Renderer.renderItem(itemOpt.get)
    itemOpt.get
  }

  def displayPage(page: Int, pageSize: Int, ids: Array[String]): Array[Item] = {
    if (page <= 0 || pageSize <= 0 || (page - 1) * pageSize > ids.length) {
      throw new IllegalArgumentException("There are no more items on page " + page + " " +
        "(page size = " + pageSize + ", number of stories available = " + ids.length + ")")
    }
    val displayedItems = new ArrayBuffer[Item]()
    for (id <- ids.slice((page - 1) * pageSize, page * pageSize)) {
      try {
        val item = display(id)
        displayedItems += item
      } catch {
        case _: NoSuchElementException => // skip
      }
    }
    displayedItems.toArray
  }

  def displayIf(ids: Array[String], cond: Item => Boolean): Array[Item] = {
    val displayedItems = new ArrayBuffer[Item]()
    for(itemId <- ids){
      val itemOpt = apiClient.getItem(itemId)
      if(itemOpt.isDefined) {
        val item = itemOpt.get
        if (cond(item)) {
          displayedItems += item
          Renderer.renderItem(item)
        }
      }
    }
    displayedItems.toArray
  }
}