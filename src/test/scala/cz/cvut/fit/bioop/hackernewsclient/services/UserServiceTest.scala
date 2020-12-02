package cz.cvut.fit.bioop.hackernewsclient.services

import org.scalatest.funsuite.AnyFunSuite

class UserServiceTest extends AnyFunSuite {

  test("testDisplayUser exception when user doesn't exist") {
    val nonExistentUser = "somebodyThatHopefullyDoesntExist256315722457"
    val thrown = intercept[NoSuchElementException] {
      UserService.displayUser(nonExistentUser)
    }
    assert(thrown.getMessage == "User " + nonExistentUser + " doesn't exist")
  }

}
