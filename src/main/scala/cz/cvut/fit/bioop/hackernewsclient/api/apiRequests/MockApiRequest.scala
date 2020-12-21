package cz.cvut.fit.bioop.hackernewsclient.api.apiRequests

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseWriters.ResponseWriter

import scala.collection.mutable.ArrayBuffer

/**
 * Mock class for ApiRequest
 *
 * It is created with required data and just returns them when asked
 */
class MockApiRequest(val items: ArrayBuffer[Item] = ArrayBuffer[Item](),
                     val users: ArrayBuffer[User] = ArrayBuffer[User](),
                     val updates: Updates = Updates(Array(), Array()),
                     val topStories: ArrayBuffer[String] = ArrayBuffer[String](),
                     val newStories: ArrayBuffer[String] = ArrayBuffer[String](),
                     val bestStories: ArrayBuffer[String] = ArrayBuffer[String]())
  extends ApiRequest {

  override def getItem(id: String): String = {
    val itemOpt = items.find(item => item.id.toString == id)
    if(itemOpt.isDefined)
      ResponseWriter.fromItem(itemOpt.get)
    else
      "null"
  }

  override def getUser(id: String): String = {
    val userOpt = users.find(user => user.id == id)
    if(userOpt.isDefined)
      ResponseWriter.fromUser(userOpt.get)
    else
      "null"
  }

  override def getTopStories: String = topStories.mkString("[", ",", "]")

  override def getNewStories: String = newStories.mkString("[", ",", "]")

  override def getBestStories: String = bestStories.mkString("[", ",", "]")

  override def getUpdates: String = ResponseWriter.fromUpdates(updates)
}
