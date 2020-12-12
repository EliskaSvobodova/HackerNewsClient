package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.MockApiRequest
import cz.cvut.fit.bioop.hackernewsclient.cache.MockCache
import org.scalatest.funsuite.AnyFunSuite

class ApiClientTest extends AnyFunSuite {
  test("testGetItem not in cache, request ok") {
    val cache = new MockCache
    val apiClient = new V0ApiClient(new MockApiRequest, cache)

    val item = apiClient.getItem(42)
    assert(item.isDefined)
    assert(item.get.id == 42)
    assert(cache.getItem(42).isDefined)
  }

  test("testGetItem in cache") {
    val cache = new MockCache
    cache.cacheItem(new Item(42))
    val apiClient = new V0ApiClient(new MockApiRequest, cache)

    val item = apiClient.getItem(42)
    assert(item.isDefined)
    assert(item.get.id == 42)
    assert(cache.getItem(42).isDefined)
  }
}
