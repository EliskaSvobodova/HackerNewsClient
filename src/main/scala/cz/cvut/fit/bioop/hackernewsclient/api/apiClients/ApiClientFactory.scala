package cz.cvut.fit.bioop.hackernewsclient.api.apiClients

import cz.cvut.fit.bioop.hackernewsclient.api.apiRequests.HackerNewsApiRequest
import cz.cvut.fit.bioop.hackernewsclient.cache.InMemoryCache

object ApiClientFactory {
  /**
   * Get default ApiClient
   */
  def apply(): ApiClient = {
    new V0ApiClient(new HackerNewsApiRequest, new InMemoryCache())
  }
}
