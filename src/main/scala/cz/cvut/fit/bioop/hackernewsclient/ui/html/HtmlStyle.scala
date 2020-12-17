package cz.cvut.fit.bioop.hackernewsclient.ui.html

import scala.collection.mutable.ArrayBuffer

object HtmlStyle extends Enumeration {
  val bold: HtmlStyle.Value = Value("1")
  val italics: HtmlStyle.Value = Value("3")
}

class HtmlStyle {
  private var styles: ArrayBuffer[HtmlStyle.Value]  = ArrayBuffer()

  def add(style: HtmlStyle.Value): HtmlStyle = {
    styles += style
    this
  }

  def remove(style: HtmlStyle.Value): HtmlStyle = {
    styles -= style
    this
  }

  def get: String = {
    if(styles.nonEmpty)
      "\u001b[0m" + styles.mkString("\u001b[", ";", "m")
    else
      "\u001b[0m"
  }

  def addAndGet(style: HtmlStyle.Value): String = {
    add(style)
    get
  }

  def removeAndGet(style: HtmlStyle.Value): String = {
    remove(style)
    get
  }
}