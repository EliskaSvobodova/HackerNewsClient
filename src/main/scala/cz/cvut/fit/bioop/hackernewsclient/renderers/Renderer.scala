package cz.cvut.fit.bioop.hackernewsclient.renderers

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

object Renderer {
  val renderer: Renderer = new StdoutRenderer

  def renderItem(item: Item): Unit = {
    renderer.renderItem(item)
  }

  def renderUser(user: User): Unit = {
    renderer.renderUser(user)
  }
}

trait Renderer {
  def renderItem(item: Item)
  def renderUser(user: User)
}