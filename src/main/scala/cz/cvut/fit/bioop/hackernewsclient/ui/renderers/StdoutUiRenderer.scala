package cz.cvut.fit.bioop.hackernewsclient.ui.renderers

class StdoutUiRenderer extends UiRenderer {
  override def render(text: String): Unit = println(text)
}
