package cz.cvut.fit.bioop.hackernewsclient.services

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object UserService {
  /**
   * Displays user's information and then fetches user's items one at a time according to display parameter
   * @param userId user that should be displayed
   */
  def displayUser(userId: String): User = {
    val userOpt = ApiClient.getUser(userId)
    if(userOpt.isEmpty){
      throw new NoSuchElementException("User " + userId + " doesn't exist")
    }
    val user = userOpt.get
    Renderer.renderUser(user)
    user
  }

  /**
   * Displays user's information and then fetches user's items one at a time according to display parameter
   * @param user user whose contributions should be displayed
   * @param types TreeSet of strings with item types that should be displayed (ex. "story", "comment")
   */
  def displayUserContribs(user: User, types: mutable.TreeSet[String]): Array[Item] = {
    if(types.isEmpty)
      return Array()

    val displayedItems = new ArrayBuffer[Item]()
    for(itemId <- user.submitted){
      val itemOpt = ApiClient.getItem(itemId)
      if(itemOpt.isDefined) {
        val item = itemOpt.get
        if (!item.deleted && types.contains(item.itemType)) {
          displayedItems += item
          Renderer.renderItem(item)
        }
      }
    }
    displayedItems.toArray
  }
}
