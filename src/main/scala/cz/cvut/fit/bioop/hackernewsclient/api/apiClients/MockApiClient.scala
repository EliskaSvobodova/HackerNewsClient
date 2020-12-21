package cz.cvut.fit.bioop.hackernewsclient.api.apiClients
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}

/**
 * Mock class for ApiClient
 *
 * It is created with required data and just returns them when asked
 */
class MockApiClient(val items: Array[Item] = Array(),
                    val users: Array[User] = Array(),
                    val topStories: Array[String] = Array(),
                    val newStories: Array[String] = Array(),
                    val bestStories: Array[String] = Array(),
                    val updates: Updates = new Updates(Array(), Array()))
  extends ApiClient {

  override def getItem(id: String): Option[Item] =
    items.find(item => item.id.toString == id)

  override def getItems(ids: Array[String]): Array[Item] =
    items.filter(item => ids.contains(item.id.toString))

  override def getUser(id: String): Option[User] =
    users.find(user => user.id == id)

  override def getTopStories: Array[String] = topStories

  override def getNewStories: Array[String] = newStories

  override def getBestStories: Array[String] = bestStories

  override def getUpdates: Updates = updates
}
