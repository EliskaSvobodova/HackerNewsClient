package cz.cvut.fit.bioop.hackernewsclient.ui.builders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

/**
 * Constructs UI elements and returns them
 */
trait UiBuilder[T] {
  def buildItemUi(item: Item): T
  def buildUserUi(user: User): T
  def buildHelpUi(message: String): T
  def buildErrorUi(message: String): T
}