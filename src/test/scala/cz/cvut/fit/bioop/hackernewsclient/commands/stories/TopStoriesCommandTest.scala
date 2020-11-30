package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException}
import org.scalatest.funsuite.AnyFunSuite

class TopStoriesCommandTest extends AnyFunSuite {

  test("testGetOptions given values") {
    val command = new TopStoriesCommand(AppOptions(), Array("--page=10", "--page-size=30"))
    val options = command.getOptions
    assert(options.page == 10)
    assert(options.pageSize == 30)
  }

  test("testGetOptions defaults") {
    val command = new TopStoriesCommand(AppOptions(), Array())
    val options = command.getOptions
    assert(options.page == 1)
    assert(options.pageSize == 10)
  }

  test("testGetOptions help") {
    val command = new TopStoriesCommand(AppOptions(), Array("--help"))
    intercept[HelpException]{
      command.getOptions
    }
  }

  test("testGetOptions unknown option") {
    val command = new TopStoriesCommand(AppOptions(), Array("--dontknow"))
    val thrown = intercept[IllegalArgumentException]{
      command.getOptions
    }
    assert(thrown.getMessage == "Unknown option \"--dontknow\", " +
      "try hackernewsclient --help for possible options")
  }

}
