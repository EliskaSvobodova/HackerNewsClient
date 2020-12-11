package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.Main.argsSplit
import org.scalatest.funsuite.AnyFunSuite

class MainTest extends AnyFunSuite {
  test("argsSplit basic") {
    val sArgs = argsSplit(Array("--a", "command", "--opt"))
    assert(sArgs._1 sameElements Array("--a"))
    assert(sArgs._2 sameElements Array("command", "--opt"))
  }

  test("argsSplit no command") {
    val sArgs = argsSplit(Array("--a"))
    assert(sArgs._1 sameElements Array("--a"))
    assert(sArgs._2.isEmpty)
  }

  test("argsSplit no app options") {
    val sArgs = argsSplit(Array("command", "--opt"))
    assert(sArgs._1.isEmpty)
    assert(sArgs._2 sameElements Array("command", "--opt"))
  }

  test("argsSplit no arguments") {
    val sArgs = argsSplit(Array())
    assert(sArgs._1.isEmpty)
    assert(sArgs._2.isEmpty)
  }

  test("argsSplit more options") {
    val sArgs = argsSplit(Array("--a", "--b", "--c", "--d", "command", "--opt1", "--opt2", "--opt3"))
    assert(sArgs._1 sameElements Array("--a", "--b", "--c", "--d"))
    assert(sArgs._2 sameElements Array("command", "--opt1", "--opt2", "--opt3"))
  }
}
