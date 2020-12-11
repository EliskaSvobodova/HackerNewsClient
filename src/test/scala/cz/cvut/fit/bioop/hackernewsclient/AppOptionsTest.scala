package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.logger.LoggerSeverity
import org.scalatest.funsuite.AnyFunSuite

class AppOptionsTest extends AnyFunSuite {
  test("parseAppOptions help") {
    val appOptions = AppOptions(Array("--help"))
    assert(appOptions.help)
  }

  test("parseAppOptions no help") {
    val appOptions = AppOptions(Array[String]())
    assert(!appOptions.help)
  }

  test("parseAppOptions log") {
    val appOptions = AppOptions(Array("--log=warning"))
    assert(appOptions.log == LoggerSeverity.warning)
  }

  test("parseAppOptions log unknown severity") {
    val thrown = intercept[IllegalArgumentException] {
      AppOptions(Array("--log=unknown"))
    }
    assert(thrown.getMessage == "Unknown application option, try --help for possible options")
  }

  test("parseAppOptions illegal argument exception") {
    val thrown = intercept[IllegalArgumentException]{
      AppOptions(Array("--nonExistentOption"))
    }
    assert(thrown.getMessage == "Unknown application option, try --help for possible options")
  }
}
