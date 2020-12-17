package cz.cvut.fit.bioop.hackernewsclient.ui.builders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

trait UiBuilder {
  def buildItemUi(item: Item)
  def buildUserUi(user: User)
  def BuildHelpUi(message: String)
  def BuildErrorUi(message: String)
}

object UiBuilder extends UiBuilder {
  val uiBuilder: UiBuilder = new StdoutUiBuilder

  def buildItemUi(item: Item): Unit = {
    uiBuilder.buildItemUi(item)
  }

  def buildUserUi(user: User): Unit = {
    uiBuilder.buildUserUi(user)
  }

  def BuildHelpUi(message: String): Unit = {
    uiBuilder.BuildHelpUi(message)
  }

  def BuildErrorUi(message: String): Unit = {
    uiBuilder.BuildErrorUi(message)
  }
}