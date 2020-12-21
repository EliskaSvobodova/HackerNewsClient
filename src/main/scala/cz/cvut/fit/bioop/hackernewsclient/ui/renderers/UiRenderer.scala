package cz.cvut.fit.bioop.hackernewsclient.ui.renderers

/**
 * Renders given text to the output
 */
trait UiRenderer {
  def render(text: String): Unit
}
