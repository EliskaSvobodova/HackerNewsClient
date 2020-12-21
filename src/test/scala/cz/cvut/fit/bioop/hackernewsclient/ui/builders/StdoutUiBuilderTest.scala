package cz.cvut.fit.bioop.hackernewsclient.ui.builders

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import org.scalatest.funsuite.AnyFunSuite

class StdoutUiBuilderTest extends AnyFunSuite {
  val story: Item = ResponseReader.toItem(
    "{" +
      "\"id\":25439878," +
      "\"type\":\"story\"," +
      "\"by\":\"gesaint\"," +
      "\"time\":1608102339," +
      "\"kids\":[25454635,25454811,25455466,25454760]," +
      "\"url\":\"https://www.cockroachlabs.com/blog/why-postgres/\"," +
      "\"score\":88," +
      "\"title\":\"Why CockroachDB and PostgreSQL Are Compatible\"," +
      "\"parts\":[]," +
      "\"descendants\":21}").get

  val comment: Item = ResponseReader.toItem(
    "{" +
      "\"id\":25497476," +
      "\"type\":\"comment\"," +
      "\"by\":\"saleheen\"," +
      "\"time\":1608573664," +
      "\"text\":\"<b><i>Great</b> Post</i>!\"," +
      "\"parent\":25497050," +
      "\"kids\":[]," +
      "\"parts\":[]}").get

  val job: Item = ResponseReader.toItem("{" +
    "\"id\":25496907," +
    "\"type\":\"job\"," +
    "\"by\":\"mattmarcus\"," +
    "\"time\":1608570668," +
    "\"kids\":[]," +
    "\"url\":\"https://angel.co/company/moderntreasury\"," +
    "\"score\":1," +
    "\"title\":\"Modern Treasury (YC S18) is hiring engineers and designer #2\"," +
    "\"parts\":[]}").get

  val poll: Item = ResponseReader.toItem("{" +
    "\"id\":126809," +
    "\"type\":\"poll\"," +
    "\"by\":\"pg\"," +
    "\"time\":1204403652," +
    "\"kids\":[126822,126823,126917,126993,126824,126934,127411,126888,127681,126818,126816,126854,127095,126861,127313,127299,126859,126852,126882,126832,127072,127217,126889,126875,127535]," +
    "\"score\":47," +
    "\"title\":\"Poll: What would happen if News.YC had explicit support for polls?\"," +
    "\"parts\":[126810,126811,126812]," +
    "\"descendants\":54}").get

  val pollopt: Item = ResponseReader.toItem("{" +
    "\"id\":126810," +
    "\"type\":\"pollopt\"," +
    "\"by\":\"pg\"," +
    "\"time\":1204403652," +
    "\"text\":\"Users would create too many, and new arrivals would think News.YC was a poll site.\"," +
    "\"poll\":126809," +
    "\"kids\":[]," +
    "\"score\":73," +
    "\"parts\":[]}").get

  val user: User = ResponseReader.toUser("{" +
    "\"id\":\"pg\"," +
    "\"created\":1160418092," +
    "\"karma\":156236," +
    "\"about\":\"Bug fixer.\"," +
    "\"submitted\":[22654365,22654342,21505741,21256727]}").get

  test("testBuildItemUi story") {
    val uiBuilder = new StdoutUiBuilder
    val itemUi = uiBuilder.buildItemUi(story)
    val shouldBe =
      "\u001b[0m\u001b[1mStory: Why CockroachDB and PostgreSQL Are Compatible\u001b[0m" +
      "(https://www.cockroachlabs.com/blog/why-postgres/)\n" +
      "item id: 25439878, 88 points, by gesaint | 21 comments\n"
    assert(itemUi == shouldBe)
  }

  test("testBuildItemUi comment") {
    val uiBuilder = new StdoutUiBuilder
    val itemUi = uiBuilder.buildItemUi(comment)
    val shouldBe =
      "\u001b[0m\u001b[1mComment: \u001b[0m" +
      "\u001b[0m\u001b[1m\u001b[0m\u001b[1;3mGreat\u001b[0m\u001b[3m Post\u001b[0m!\n" +
      "item id: 25497476, 0 points, by saleheen | 0 comments\n"
    assert(itemUi == shouldBe)
  }

  test("testBuildItemUi job") {
    val uiBuilder = new StdoutUiBuilder
    val itemUi = uiBuilder.buildItemUi(job)
    val shouldBe =
      "\u001b[0m\u001b[1mJob: Modern Treasury (YC S18) is hiring engineers and designer #2\u001b[0m 2020-12-21\n" +
      "no text\n" +
      "item id: 25496907, 1 points, by mattmarcus | 0 comments\n"
    assert(itemUi == shouldBe)
  }

  test("testBuildItemUi poll") {
    val uiBuilder = new StdoutUiBuilder
    val itemUi = uiBuilder.buildItemUi(poll)
    val shouldBe =
      "\u001b[0m\u001b[1mPoll: Poll: What would happen if News.YC had explicit support for polls?\u001b[0m\n" +
        "parts: 126810,126811,126812\n" +
        "item id: 126809, 47 points, by pg | 54 comments\n"
    assert(itemUi == shouldBe)
  }

  test("testBuildItemUi pollopt") {
    val uiBuilder = new StdoutUiBuilder
    val itemUi = uiBuilder.buildItemUi(pollopt)
    val shouldBe =
      "\u001b[0m\u001b[1mPollopt:\u001b[0m\n" +
      "Users would create too many, and new arrivals would think News.YC was a poll site.\n" +
      "item id: 126810, 73 points, by pg | 0 comments\n"
    assert(itemUi == shouldBe)
  }

  test("testBuildHelpUi") {
    val uiBuilder = new StdoutUiBuilder
    val helpUi = uiBuilder.buildHelpUi("helpful string")
    assert(helpUi == "helpful string")
  }

  test("testBuildErrorUi") {
    val uiBuilder = new StdoutUiBuilder
    val errorUi = uiBuilder.buildErrorUi("Error meh..")
    assert(errorUi == "\\e[0;31mError meh..\\e[m")
  }

  test("testBuildUserUi") {
    val uiBuilder = new StdoutUiBuilder
    val userUi = uiBuilder.buildUserUi(user)
    val shouldBe =
      "user :     \u001b[0m\u001b[1mpg\u001b[0m\n" +
      "created:   2006-10-09\n" +
      "karma:     156236\n" +
      "about:     Bug fixer.\n" +
      "submitted: 4 items\n"
    assert(userUi == shouldBe)
  }

}
