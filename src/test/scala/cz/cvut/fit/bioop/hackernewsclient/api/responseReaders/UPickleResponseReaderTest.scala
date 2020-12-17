package cz.cvut.fit.bioop.hackernewsclient.api.responseReaders

import org.scalatest.funsuite.AnyFunSuite

class UPickleResponseReaderTest extends AnyFunSuite {
  test("testToItem") {
    val testResponse = "{\"by\":\"dhouston\",\"descendants\":71,\"id\":8863,\"kids\":[9224,8917],\"score\":104,\"time\":1175714200,\"title\":\"My YC app: Dropbox - Throw away your USB drive\",\"type\":\"story\",\"url\":\"http://www.getdropbox.com/u/2/screencast.html\"}"
    val item = ResponseReader.toItem(testResponse).get
    assert(item.id == "8863")
    assert(!item.deleted)
    assert(item.itemType == "story")
    assert(item.by == "dhouston")
    assert(item.time == 1175714200)
    assert(item.text == "no text")
    assert(!item.dead)
    assert(item.parent == "")
    assert(item.poll == -1)
    assert(item.kids sameElements Array(9224,8917))
    assert(item.url == "http://www.getdropbox.com/u/2/screencast.html")
    assert(item.score == 104)
    assert(item.title == "My YC app: Dropbox - Throw away your USB drive")
    assert(item.parts.isEmpty)
    assert(item.descendants == 71)
  }

  test("testToArrayOfStoriesIds with elements") {
    val testResponse = "[1,2,33,123]"
    val ids = ResponseReader.toArrayOfItemIds(testResponse)
    assert(ids sameElements Array("1", "2", "33", "123"))
  }

  test("testToArrayOfStoriesIds empty") {
    val testResponse = "[]"
    val thrown = intercept[IllegalArgumentException]{
      ResponseReader.toArrayOfItemIds(testResponse)
    }
    assert(thrown.getMessage == "Empty response")
  }

  test("testToUser") {
    val testResponse = "{\"about\":\"http://norvig.com\",\"created\":1190398535,\"id\":\"norvig\",\"karma\":690,\"submitted\":[22072713,18144213,17832839,10328571,10328326]}"
    val user = ResponseReader.toUser(testResponse).get
    assert(user.id == "norvig")
    assert(user.delay == -1)
    assert(user.created == 1190398535)
    assert(user.karma == 690)
    assert(user.about == "http://norvig.com")
    assert(user.submitted sameElements Array(22072713,18144213,17832839,10328571,10328326))
  }
}
