package cz.cvut.fit.bioop.hackernewsclient.api.apiRequests

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import cz.cvut.fit.bioop.hackernewsclient.api.responseWriters.ResponseWriter

class MockApiRequest extends ApiRequest {
  override def getItem(id: String): String = ResponseWriter.fromItem(Item("42"))

  override def getUser(id: String): String = "null"

  override def getTopStories: String = ResponseWriter.fromArrayOfItemIds(Array("42", "43", "44"))

  override def getNewStories: String = ResponseWriter.fromArrayOfItemIds(Array("42", "43", "44"))

  override def getBestStories: String = ResponseWriter.fromArrayOfItemIds(Array("42", "43", "44"))

  override def getUpdates: String = ResponseWriter.fromArrayOfItemIds(Array("42", "43", "44"))
}
