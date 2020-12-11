package cz.cvut.fit.bioop.hackernewsclient

import scala.util.matching.Regex

class HtmlConverter {
  private val quotRe: Regex = "&quot;(.*)".r
  private val aposRe: Regex = "&apos;(.*)".r
  private val ampRe:  Regex = "&amp;(.*)".r
  private val ltRe:   Regex = "&lt;(.*)".r
  private val gtRe:   Regex = "&gt;(.*)".r

  private val parRe:      Regex = "<p>(.*)".r
  private val parEndRe:   Regex = "</p>(.*)".r
  private val itRe:       Regex = "<i>(.*)".r
  private val itEndRe:    Regex = "</i>(.*)".r
  private val emRe:       Regex = "<em>(.*)".r
  private val emEndRe:    Regex = "</em>(.*)".r
  private val boldRe:     Regex = "<b>(.*)".r
  private val boldEndRe:  Regex = "</b>(.*)".r
  private val strongRe:   Regex = "<strong>(.*)".r
  private val strongEndRe:Regex = "</strong>(.*)".r
  private val otherRe:    Regex = "<.+>(.*)".r
  private val otherEndRe: Regex = "</.+>(.*)".r

  def printHtml(text: String): Unit = {
    println(convertTags(convertSpecialChars(text)))
  }

  def convertTags(text: String): String = {
    val parts = text.split("(?=<)")
    val result = new StringBuilder()

    for(part <- parts){
      part match {
        case parRe(rest)      => result.append("\n" + rest)
        case parEndRe(rest)   => result.append(rest)
        case itRe(rest)       => result.append("\u001b[3m" + rest)
        case itEndRe(rest)    => result.append("\u001b[0m" + rest)
        case emRe(rest)       => result.append("\u001b[3m" + rest)
        case emEndRe(rest)    => result.append("\u001b[0m" + rest)
        case boldRe(rest)     => result.append("\u001b[1m" + rest)
        case boldEndRe(rest)  => result.append("\u001b[0m" + rest)
        case strongRe(rest)   => result.append("\u001b[1m" + rest)
        case strongEndRe(rest)=> result.append("\u001b[0m" + rest)
        case otherRe(rest)    => result.append(rest)
        case otherEndRe(rest) => result.append(rest)
        case rest             => result.append(rest)
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
