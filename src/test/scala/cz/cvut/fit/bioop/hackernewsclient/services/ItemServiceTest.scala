package cz.cvut.fit.bioop.hackernewsclient.services

import org.scalatest.funsuite.AnyFunSuite

class ItemServiceTest extends AnyFunSuite {

  test("displayPage exception when page bigger than range") {
    val thrownPageNum = intercept[IllegalArgumentException] {
      ItemService.displayPage(10, 1, Array(1, 2, 3))
    }
    assert(thrownPageNum.getMessage === "There are no more items on page 10 (page size = 1, number of stories available = 3)")

    val thrownPageSize = intercept[IllegalArgumentException] {
      ItemService.displayPage(2, 10, Array(1, 2, 3))
    }
    assert(thrownPageSize.getMessage === "There are no more items on page 2 (page size = 10, number of stories available = 3)")
  }

  test("displayPage exception when page lower than range") {
    val thrownPageNum = intercept[IllegalArgumentException] {
      ItemService.displayPage(-1, 10, Array(1, 2, 3))
    }
    assert(thrownPageNum.getMessage === "There are no more items on page -1 (page size = 10, number of stories available = 3)")

    val thrownPageSize = intercept[IllegalArgumentException] {
      ItemService.displayPage(1, -1, Array(1, 2, 3))
    }
    assert(thrownPageSize.getMessage === "There are no more items on page 1 (page size = -1, number of stories available = 3)")
  }

}