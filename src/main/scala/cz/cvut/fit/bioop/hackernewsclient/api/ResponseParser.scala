package cz.cvut.fit.bioop.hackernewsclient.api

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item
import upickle.default.read

object ResponseParser {
  def toItem(response: String): Item = {
    read[Item](response)
  }
}
