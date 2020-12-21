package cz.cvut.fit.bioop.hackernewsclient.services

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.{ApiClient, ApiClientFactory}
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.User
import cz.cvut.fit.bioop.hackernewsclient.ui.Ui

/**
 * Displays user, is ready for implementing lazy fetching
 */
class UserService(val apiClient: ApiClient = ApiClientFactory()) {
  /**
   * Displays a user with given id
   */
  def display(id: String): User = {
    val userOpt = apiClient.getUser(id)
    if(userOpt.isEmpty){
      throw new NoSuchElementException("User " + id + " doesn't exist")
    }
    val user = userOpt.get
    Ui.displayUser(user)
    user
  }
}