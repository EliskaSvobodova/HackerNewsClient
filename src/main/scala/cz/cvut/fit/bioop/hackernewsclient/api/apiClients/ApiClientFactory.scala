package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.HackerNewsApiRequest
import cz.cvut.fit.bioop.hackernewsclient.cache.InMemoryCache

/**
 * Creates default ApiClient
 */
object ApiClientFactory {
  def apply(): ApiClient = {
    new V0ApiClient(new HackerNewsApiRequest, new InMemoryCache())
  }
}
