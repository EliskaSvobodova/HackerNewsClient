package cz.cvut.fit.bioop.hackernewsclient.api

import scala.io.Source

object RequestUrl {
  def get(url: String) = {
    val source = Source.fromURL(url)
    val content = source.mkString
    source.close()
    content
  }
}
