package cz.cvut.fit.bioop.hackernewsclient.services

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.{ApiClient, ApiClientFactory}
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.User
import cz.cvut.fit.bioop.hackernewsclient.renderers.Renderer

class UserService(val apiClient: ApiClient = ApiClientFactory()) {
  def display(id: String): User = {
    val userOpt = apiClient.getUser(id)
    if(userOpt.isEmpty){
      throw new NoSuchElementException("User " + id + " doesn't exist")
    }
    val user = userOpt.get
    Renderer.renderUser(user)
    user
  }
}