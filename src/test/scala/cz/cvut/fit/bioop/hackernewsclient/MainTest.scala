package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.Main.appOptionsSplit
import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {
  test("appOptionsSplit basic") {
    val sArgs = appOptionsSplit(Array("--a", "command", "--opt"))
    assert(sArgs._1 sameElements Array("--a"))
    assert(sArgs._2 sameElements Array("command", "--opt"))
  }

  test("appOptionsSplit no command") {
    val sArgs = appOptionsSplit(Array("--a"))
    assert(sArgs._1 sameElements Array("--a"))
    assert(sArgs._2.isEmpty)
  }

  test("appOptionsSplit no app options") {
    val sArgs = appOptionsSplit(Array("command", "--opt"))
    assert(sArgs._1.isEmpty)
    assert(sArgs._2 sameElements Array("command", "--opt"))
  }

  test("appOptionsSplit no arguments") {
    val sArgs = appOptionsSplit(Array())
    assert(sArgs._1.isEmpty)
    assert(sArgs._2.isEmpty)
  }

  test("appOptionsSplit more options") {
    val sArgs = appOptionsSplit(Array("--a", "--b", "--c", "--d", "command", "--opt1", "--opt2", "--opt3"))
    assert(sArgs._1 sameElements Array("--a", "--b", "--c", "--d"))
    assert(sArgs._2 sameElements Array("command", "--opt1", "--opt2", "--opt3"))
  }
}
