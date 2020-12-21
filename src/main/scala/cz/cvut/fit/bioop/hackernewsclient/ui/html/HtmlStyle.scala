package cz.cvut.fit.bioop.hackernewsclient.ui.html

import scala.collection.mutable.ArrayBuffer

/**
 * Supported html styles
 */
object HtmlStyle extends Enumeration {
  val bold: HtmlStyle.Value = Value("1")
  val italics: HtmlStyle.Value = Value("3")
}

class HtmlStyle {
  /**
   * Currently used styles
   */
  private var styles: ArrayBuffer[HtmlStyle.Value]  = ArrayBuffer()

  /**
   * Add a new style to be used next
   */
  def add(style: HtmlStyle.Value): HtmlStyle = {
    styles += style
    this
  }

  /**
   * Removes a style from an array of currently used styles
   */
  def remove(style: HtmlStyle.Value): HtmlStyle = {
    styles -= style
    this
  }

  /**
   * Returns unicode symbol to be added before string that is supposed to be formatted
   */
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