package cz.cvut.fit.bioop.hackernewsclient.renderers.html

import scala.util.matching.Regex

class HtmlConverter {
  private val quotRe: Regex = "&quot;(.*)".r
  private val aposRe: Regex = "&apos;(.*)".r
  private val ampRe:  Regex = "&amp;(.*)".r
  private val ltRe:   Regex = "&lt;(.*)".r
  private val gtRe:   Regex = "&gt;(.*)".r

  private val parRe:      Regex = "<p>(.*)".r
  private val parEndRe:   Regex = "</p>(.*)".r
  private val itRe:       Regex = "(<i>|<em>)(.*)".r
  private val itEndRe:    Regex = "(</i>|</em>)(.*)".r
  private val boldRe:     Regex = "(<b>|<strong>)(.*)".r
  private val boldEndRe:  Regex = "(</b>|</strong>)(.*)".r
  private val otherRe:    Regex = "(<.+>|</.+>)(.*)".r

  def printHtml(text: String): Unit = {
    println(convertTags(convertSpecialChars(text)))
  }

  def convertTags(text: String): String = {
    val parts = text.split("(?=<)")
    val htmlStyle = new HtmlStyle
    val result = new StringBuilder()

    for(part <- parts){
      part match {
        case parRe(rest)         => result.append("\n" + rest)
        case parEndRe(rest)      => result.append(rest)
        case itRe(_, rest)       => result.append(htmlStyle.addAndGet(HtmlStyle.italics) + rest)
        case itEndRe(_, rest)    => result.append(htmlStyle.removeAndGet(HtmlStyle.italics) + rest)
        case boldRe(_, rest)     => result.append(htmlStyle.addAndGet(HtmlStyle.bold) + rest)
        case boldEndRe(_, rest)  => result.append(htmlStyle.removeAndGet(HtmlStyle.bold) + rest)
        case otherRe(_, rest)    => result.append(rest)
        case rest                => result.append(rest)
      }
    }

    result.toString()
  }

  def convertSpecialChars(text: String): String = {
    val parts = text.split("(?=&)")  // ?= keeps & character
    val result = new StringBuilder()

    for(part <- parts){
      part match {
        case quotRe(rest) => result.append('"' + rest)
        case aposRe(rest) => result.append("'" + rest)
        case ampRe(rest)  => result.append("&" + rest)
        case ltRe(rest)   => result.append("<" + rest)
        case gtRe(rest)   => result.append(">" + rest)
        case rest         => result.append(rest)
      }
    }

    result.toString()
  }
}
