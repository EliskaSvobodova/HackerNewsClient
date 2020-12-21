package cz.cvut.fit.bioop.hackernewsclient.ui.builders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

/**
 * Constructs UI elements and returns them
 */
trait UiBuilder {
  def buildItemUi(item: Item): String
  def buildUserUi(user: User): String
  def buildHelpUi(message: String): String
  def buildErrorUi(message: String): String
}