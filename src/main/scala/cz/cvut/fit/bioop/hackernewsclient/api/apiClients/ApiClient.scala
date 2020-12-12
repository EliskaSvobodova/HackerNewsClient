package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.HackerNewsApiRequest

trait ApiClient {
  def getItem(id: Long): Option[Item]
  def getItems(ids: Array[Long]): Array[Item]
  def getUser(id: String): Option[User]
  def getTopStories: Array[Long]
  def getNewStories: Array[Long]
  def getBestStories: Array[Long]
  def getUpdates: Updates
}

object ApiClient extends ApiClient {
  val apiClient: ApiClient = new V0ApiClient(new HackerNewsApiRequest)

  def getItem(id: Long): Option[Item] = {
    apiClient.getItem(id)
  }

  def getItems(ids: Array[Long]): Array[Item] = {
    apiClient.getItems(ids)
  }

  def getUser(id: String): Option[User] = {
    apiClient.getUser(id)
  }

  def getTopStories: Array[Long] = {
    apiClient.getTopStories
  }

  def getNewStories: Array[Long] = {
    apiClient.getNewStories
  }

  def getBestStories: Array[Long] = {
    apiClient.getBestStories
  }

  def getUpdates: Updates = {
    apiClient.getUpdates
  }
}
