package cz.cvut.fit.bioop.hackernewsclient

import org.scalatest.funsuite.AnyFunSuite

class AppOptionsTest extends AnyFunSuite {
  test("parseAppOptions help") {
    val appOptions = AppOptions.parseAppOptions(Array("--help"))
    assert(appOptions.help)
  }

  test("parseAppOptions no help") {
    val appOptions = AppOptions.parseAppOptions(Array())
    assert(!appOptions.help)
  }

  test("parseAppOptions illegal argument exception") {
    val thrown = intercept[IllegalArgumentException]{
      AppOptions.parseAppOptions(Array("--nonExistentOption"))
    }
    assert(thrown.getMessage == "Unknown application option, try --help for possible options")
  }
}
