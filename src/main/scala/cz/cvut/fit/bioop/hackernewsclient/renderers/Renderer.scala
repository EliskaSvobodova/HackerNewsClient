package cz.cvut.fit.bioop.hackernewsclient.renderers

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.Item

object Renderer {
  val renderer: Renderer = new StdoutRenderer

  def renderItem(item: Item) = {
    renderer.renderItem(item)
  }
}

trait Renderer {
  def renderItem(item: Item)
}