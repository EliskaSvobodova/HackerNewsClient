package cz.cvut.fit.bioop.hackernewsclient.ui

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.ui.builders.StdoutUiBuilder
import cz.cvut.fit.bioop.hackernewsclient.ui.renderers.StdoutUiRenderer

/**
 * Combines Ui building and rendering
 */
object Ui {
  val uiBuilder = new StdoutUiBuilder
  val uiRenderer = new StdoutUiRenderer

  def displayItem(item: Item): Unit = uiRenderer.render(uiBuilder.buildItemUi(item))
  def displayUser(user: User): Unit = uiRenderer.render(uiBuilder.buildUserUi(user))
  def displayHelp(message: String): Unit = uiRenderer.render(uiBuilder.buildHelpUi(message))
  def displayError(message: String): Unit = uiRenderer.render(uiBuilder.buildErrorUi(message))
}
