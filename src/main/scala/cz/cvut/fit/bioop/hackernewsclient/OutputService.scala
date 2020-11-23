package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import scala.collection.mutable

/**
 * Puts together ApiClient and Renderer and performs lazy fetching
 */
object OutputService {
  def displayPage(page: Int, pageSize: Int, storiesIds: Array[Long]): Unit = {
    if ((page - 1) * pageSize > storiesIds.length) {
      Renderer.displayError("There are no more stories on page " + page)
      return
    }
    val range = Range((page - 1) * pageSize, page * pageSize)
    for {
      (_, storyId) <- range zip storiesIds
    } yield {
      val item = ApiClient.getItem(storyId)
      Renderer.renderItem(item)
    }
  }

  def displayUser(userId: String, display: mutable.TreeSet[String]): Unit = {
    val user = ApiClient.getUser(userId)
    Renderer.renderUser(user)
    if(display.nonEmpty)
      for(itemId <- user.submitted){
        val item = ApiClient.getItem(itemId)
        if(!item.deleted && display.contains(item.itemType))
          Renderer.renderItem(item)
      }
  }
}
