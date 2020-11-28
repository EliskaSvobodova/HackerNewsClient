package cz.cvut.fit.bioop.hackernewsclient

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable

class OutputServiceTest extends AnyFunSuite {
  test("displayPage exception when page bigger than range") {
    val thrownPageNum = intercept[IllegalArgumentException] {
      OutputService.displayPage(10, 1, Array(1, 2, 3))
    }
    assert(thrownPageNum.getMessage === "Invalid page number [10] with page size [1], number of stories = 3")

    val thrownPageSize = intercept[IllegalArgumentException] {
      OutputService.displayPage(2, 10, Array(1, 2, 3))
    }
    assert(thrownPageSize.getMessage === "Invalid page number [2] with page size [10], number of stories = 3")
  }

  test("displayPage exception when page lower than range") {
    val thrownPageNum = intercept[IllegalArgumentException] {
      OutputService.displayPage(-1, 10, Array(1, 2, 3))
    }
    assert(thrownPageNum.getMessage === "Invalid page number [-1] with page size [10], number of stories = 3")

    val thrownPageSize = intercept[IllegalArgumentException] {
      OutputService.displayPage(1, -1, Array(1, 2, 3))
    }
    assert(thrownPageSize.getMessage === "Invalid page number [1] with page size [-1], number of stories = 3")
  }

  test("displayPage exception when items doesn't exist") {
    val thrown = intercept[NoSuchElementException] {
      OutputService.displayPage(1, 10, Array(-1, 2, 3))
    }
    assert(thrown.getMessage == "Item with id -1 doesn't exist")
  }

  test("displayUser exception when user doesn't exist") {
    val nonExistentUser = "somebodyThatHopefullyDoesntExist256315722457"
    val thrown = intercept[NoSuchElementException] {
      OutputService.displayUser(nonExistentUser, mutable.TreeSet())
    }
    assert(thrown.getMessage == "User " + nonExistentUser + " doesn't exist")
  }
}
