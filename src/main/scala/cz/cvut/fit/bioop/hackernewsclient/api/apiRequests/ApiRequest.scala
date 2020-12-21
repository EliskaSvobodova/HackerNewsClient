package cz.cvut.fit.bioop.hackernewsclient.api.apiRequests

/**
 * Class for making requests on remote API
 */
trait ApiRequest {
  def getItem(id: String): String
  def getUser(id: String): String
  def getTopStories: String
  def getNewStories: String
  def getBestStories: String
  def getUpdates: String
}
