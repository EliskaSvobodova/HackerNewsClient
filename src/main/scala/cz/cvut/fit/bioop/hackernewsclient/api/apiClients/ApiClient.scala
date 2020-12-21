package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}

/**
 * Api client is responsible for fetching the data
 */
trait ApiClient {
  def getItem(id: String): Option[Item]
  def getItems(ids: Array[String]): Array[Item]
  def getUser(id: String): Option[User]
  def getTopStories: Array[String]
  def getNewStories: Array[String]
  def getBestStories: Array[String]
  def getUpdates: Updates
}