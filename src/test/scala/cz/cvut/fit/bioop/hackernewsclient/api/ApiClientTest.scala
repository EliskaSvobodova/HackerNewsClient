package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.ApiClient
import org.scalatest.funsuite.AnyFunSuite

class ApiClientTest extends AnyFunSuite {

  test("testGetUser non existent user") {
    val user = ApiClient.getUser("hopefullyNoOne2498357")
    assert(user.isEmpty)
  }

  test("testGetItem non existent item") {
    val item = ApiClient.getItem(-1)
    assert(item.isEmpty)
  }

}
