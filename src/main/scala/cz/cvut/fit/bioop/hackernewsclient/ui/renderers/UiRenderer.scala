package cz.cvut.fit.bioop.hackernewsclient.ui.renderers

/**
 * Renders given element to the output
 */
trait UiRenderer[T] {
  def render(text: T): Unit
}
