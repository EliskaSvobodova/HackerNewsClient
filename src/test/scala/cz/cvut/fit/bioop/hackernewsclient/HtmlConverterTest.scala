package cz.cvut.fit.bioop.hackernewsclient

import org.scalatest.funsuite.AnyFunSuite

class HtmlConverterTest extends AnyFunSuite {

  test("testConvertSpecialChars & not html") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertSpecialChars("something before. (not) easy! &quot;literally& quot; the end.")
    assert(res == "something before. (not) easy! \"literally& quot; the end.")
  }

  test("testConvertSpecialChars quote in text") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertSpecialChars("something before. (not) easy! &quot;literally&quot; the end.")
    assert(res == "something before. (not) easy! \"literally\" the end.")
  }

  test("testConvertSpecialChars apostrophe in text") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertSpecialChars("something before. (not) easy! &apos;literally&apos; the end.")
    assert(res == "something before. (not) easy! 'literally' the end.")
  }

  test("testConvertSpecialChars ampersand in text") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertSpecialChars("something before. (not) easy! &amp;literally&amp; the end.")
    assert(res == "something before. (not) easy! &literally& the end.")
  }

  test("testConvertSpecialChars less than in text") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertSpecialChars("something before. (not) easy! &lt;literally&lt; the end.")
    assert(res == "something before. (not) easy! <literally< the end.")
  }

  test("testConvertSpecialChars greater than in text") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertSpecialChars("something before. (not) easy! &gt;literally&gt; the end.")
    assert(res == "something before. (not) easy! >literally> the end.")
  }

  test("testConvertTags bold") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertTags("something before <b>BOLD</b> the end.")
    println("Testing html converter - bold: " + res)
    assert(res == "something before \u001b[1mBOLD\u001b[0m the end.")
  }

  test("testConvertTags italics") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertTags("something before <i>ITALICS</i> the end.")
    println("Testing html converter - italics: " + res)
    assert(res == "something before \u001b[3mITALICS\u001b[0m the end.")
  }

  test("testConvertTags paragraph") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertTags("<p>First paragraph.</p><p>Second paragraph.</p>")
    println("Testing html converter - paragraph: " + res)
    assert(res == "\nFirst paragraph.\nSecond paragraph.")
  }

  test("testConvertTags remove other tags") {
    val htmlConverter = new HtmlConverter()
    val res = htmlConverter.convertTags("<f>some </f>other <raf>tags<ehm>.")
    println("Testing html converter - other tags: " + res)
    assert(res == "some other tags.")
  }
}
