package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.{AppOptions, HelpException}
import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable

class UserCommandTest extends AnyFunSuite {
  test("getOptions id") {
    val command = new UserCommand(AppOptions(), Array("--id=name123"))
    val options = command.getOptions
    assert(options.id == "name123")
  }

  test("getOptions exception when no id") {
    val command = new UserCommand(AppOptions(), Array("--stories"))
    val thrown = intercept[IllegalArgumentException] {
      command.getOptions
    }
    assert(thrown.getMessage == "Missing compulsory option --id=[value], fill a user name instead of [value]")
  }

  test("getOptions exception when empty id") {
    val command = new UserCommand(AppOptions(), Array("--stories", "--id="))
    val thrown = intercept[IllegalArgumentException] {
      command.getOptions
    }
    assert(thrown.getMessage == "Missing compulsory option --id=[value], fill a user name instead of [value]")
  }

  test("getOptions display set") {
    val command = new UserCommand(AppOptions(), Array("--id=name123", "--stories", "--jobs", "--polls"))
    val options = command.getOptions
    assert(options.display == mutable.TreeSet[String]("story", "job", "poll"))
  }

  test("getOptions display set shorter option") {
    val command = new UserCommand(AppOptions(), Array("--id=name123", "-s", "-j", "-p", "-c"))
    val options = command.getOptions
    assert(options.display == mutable.TreeSet[String]("story", "comment", "job", "poll"))
  }

  test("getOptions display set all") {
    val command = new UserCommand(AppOptions(), Array("--id=name123", "--all"))
    val options = command.getOptions
    assert(options.display == mutable.TreeSet[String]("story", "comment", "job", "poll"))
  }

  test("getOptions help") {
    val command = new UserCommand(AppOptions(), Array("--stories", "--help", "--id="))
    intercept[HelpException] {
      command.getOptions
    }
  }

  test("getOptions unknown option") {
    val command = new UserCommand(AppOptions(), Array("--stories", "--it"))
    val thrown = intercept[IllegalArgumentException] {
      command.getOptions
    }
    assert(thrown.getMessage == "Unknown option \"--it\", " +
      "try hackernewsclient user --help for possible options")
  }
}
