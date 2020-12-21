package cz.cvut.fit.bioop.hackernewsclient.ui.renderers

/**
 * Prints the given text to stdout
 */
class StdoutUiRenderer extends UiRenderer[String] {
  override def render(text: String): Unit = println(text)
}
