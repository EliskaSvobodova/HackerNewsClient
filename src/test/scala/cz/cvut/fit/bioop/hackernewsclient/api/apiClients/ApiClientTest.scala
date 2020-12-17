package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, Updates, User}
import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.MockApiRequest
import cz.cvut.fit.bioop.hackernewsclient.cache.MockCache
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.ArrayBuffer

class ApiClientTest extends AnyFunSuite {
  test("testGetItem in cache") {
    val apiClient = new V0ApiClient(
      new MockApiRequest,
      new MockCache(ArrayBuffer(new Item("42"))))

    val itemOpt = apiClient.getItem("42")
    assert(itemOpt.isDefined)
    assert(itemOpt.get.id == "42")
  }

  test("testGetItem not in cache, in api") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(ArrayBuffer(new Item("42"))),
      new MockCache)

    val itemOpt = apiClient.getItem("42")
    assert(itemOpt.isDefined)
    assert(itemOpt.get.id == "42")
    assert(apiClient.cache.getItem("42").isDefined)
  }

  test("testGetItem not in cache, not in api") {
    val apiClient = new V0ApiClient(
      new MockApiRequest,
      new MockCache)

    val itemOpt = apiClient.getItem("42")
    assert(itemOpt.isEmpty)
    assert(apiClient.cache.getItem("42").isEmpty)
  }

  test("testGetItems") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(ArrayBuffer(new Item("42"), new Item("43"))),
      new MockCache(ArrayBuffer(new Item("42"))))

    val items = apiClient.getItems(Array("42", "43", "44"))
    assert(items.length == 2)
    assert(items(0).id == "42")
    assert(items(1).id == "43")
    assert(apiClient.cache.getItem("42").isDefined)
    assert(apiClient.cache.getItem("43").isDefined)
  }

  test("testGetUser in cache") {
    val apiClient = new V0ApiClient(
      new MockApiRequest,
      new MockCache(usersCache = ArrayBuffer(new User("Josh"))))

    val userOpt = apiClient.getUser("Josh")
    assert(userOpt.isDefined)
    assert(userOpt.get.id == "Josh")
  }

  test("testGetUser not in cache, in api") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(users = ArrayBuffer(new User("Josh"))),
      new MockCache)

    val userOpt = apiClient.getUser("Josh")
    assert(userOpt.isDefined)
    assert(userOpt.get.id == "Josh")
    assert(apiClient.cache.getUser("Josh").isDefined)
  }

  test("testGetUser not in cache, not in api") {
    val apiClient = new V0ApiClient(
      new MockApiRequest,
      new MockCache)

    val userOpt = apiClient.getUser("Josh")
    assert(userOpt.isEmpty)
    assert(apiClient.cache.getUser("Josh").isEmpty)
  }

  test("testGetTopStories") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(topStories = ArrayBuffer("42", "43")),
      new MockCache)

    val stories = apiClient.getTopStories
    assert(stories.length == 2)
    assert(stories sameElements Array("42", "43"))
  }

  test("testGetNewStories") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(newStories = ArrayBuffer("42", "43")),
      new MockCache)

    val stories = apiClient.getNewStories
    assert(stories.length == 2)
    assert(stories sameElements Array("42", "43"))
  }

  test("testGetBestStories") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(bestStories = ArrayBuffer("42", "43")),
      new MockCache)

    val stories = apiClient.getBestStories
    assert(stories.length == 2)
    assert(stories sameElements Array("42", "43"))
  }

  test("testGetUpdates") {
    val apiClient = new V0ApiClient(
      new MockApiRequest(updates = new Updates(Array("42", "43"), Array("Josh", "Barney"))),
      new MockCache)

    val updates = apiClient.getUpdates
    assert(updates.sameUpdates(new Updates(Array("42", "43"), Array("Josh", "Barney"))))
  }
}
