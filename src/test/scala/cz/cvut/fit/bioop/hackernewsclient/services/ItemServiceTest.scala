package cz.cvut.fit.bioop.hackernewsclient.services

import cz.cvut.fit.bioop.hackernewsclient.api.apiClients.MockApiClient
import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}
import cz.cvut.fit.bioop.hackernewsclient.api.responseReaders.ResponseReader
import org.scalatest.funsuite.AnyFunSuite

class ItemServiceTest extends AnyFunSuite {

  val storyA: Item = ResponseReader.toItem("{\"id\":25439878,\"type\":\"story\",\"by\":\"gesaint\",\"time\":1608102339,\"kids\":[25454635,25454811,25455466,25454760],\"url\":\"https://www.cockroachlabs.com/blog/why-postgres/\",\"score\":88,\"title\":\"Why CockroachDB and PostgreSQL Are Compatible\",\"parts\":[],\"descendants\":21}").get
  val storyB: Item = ResponseReader.toItem("{\"id\":25430885,\"type\":\"story\",\"by\":\"easton\",\"time\":1608044495,\"kids\":[25431563,25431618,25431854,25432029,25431792,25431416,25431991,25430891,25433899,25434333,25435781,25440269,25431621,25431787,25434360,25431689,25438838,25433456,25432842,25432606,25432050,25432591,25432301,25431725,25437889,25432174,25436927,25431424,25431921,25435582,25432312,25437617,25434141,25431788,25433268,25432771],\"url\":\"https://www.mozilla.org/en-US/firefox/84.0/releasenotes/\",\"score\":558,\"title\":\"Firefox 84.0\",\"parts\":[],\"descendants\":310}").get
  val storyC: Item = ResponseReader.toItem("{\"id\":25445725,\"type\":\"story\",\"by\":\"andyjpb\",\"time\":1608141021,\"kids\":[25448391,25449831,25452706,25452370,25448403,25449707,25449455,25454374,25448802,25451642,25449167,25447654,25446245,25452897,25449896,25448961,25450379,25453482,25448212,25452888,25446493,25446432,25446407,25447607,25451166,25453639,25448049,25447682,25450139,25447309,25446637,25450059,25450298,25449691,25447534,25446549,25447331,25448347,25452716,25446934,25447179],\"url\":\"https://github.com/rocky-linux/rocky\",\"score\":781,\"title\":\"Rocky Linux: A CentOS replacement by the CentOS founder\",\"parts\":[],\"descendants\":494}").get
  val storyD: Item = ResponseReader.toItem("{\"id\":25466304,\"type\":\"story\",\"by\":\"OJFord\",\"time\":1608293166,\"kids\":[25466465,25466594,25466661,25467942,25467606,25466924,25466874,25466468,25467754,25466805,25466342,25468410,25472194,25467919,25468463,25471121,25467532,25467216,25466655,25470177,25466666,25466384,25467178,25470136,25470300,25491389,25467052,25466529,25470866,25468272,25469549,25472134,25466642,25467065,25466709,25467167,25467934,25469889,25467822,25471740,25473220,25467403,25467199,25467713,25472064,25469129,25471624,25466751,25466436,25474292,25472403,25468933,25479508,25469623,25466356,25466575,25473176,25466653,25468834,25468014,25467777,25467855,25467385,25473743,25466920,25474729,25477340,25469756,25472985,25471993,25471870,25466602,25473564,25466388,25476359,25466741,25466591,25468197,25468052,25466678,25473211,25471656,25474720,25473332,25470387,25470027,25466896,25473265,25474451,25471890,25469828,25474480,25475416,25466644,25467210,25466842,25466365,25466747],\"url\":\"https://www.bloomberg.com/news/articles/2020-12-18/czech-startup-founders-turn-billionaires-without-vc-help\",\"score\":1225,\"title\":\"Jetbrains founders turn billionaires without VC help\",\"parts\":[],\"descendants\":510}").get
  val storyE: Item = ResponseReader.toItem("{\"id\":25482927,\"type\":\"story\",\"by\":\"chesterfield\",\"time\":1608426393,\"kids\":[25483315,25485296,25484852,25483428,25483454,25483765,25486532,25485304,25483525,25483100,25485554,25483141,25483400,25483207,25493256,25484317,25483362,25484050,25487129,25484494,25483459,25483123,25485239,25483201,25483196,25483732,25488827,25484989,25487191,25483379,25483137,25488988,25485104,25488877,25484997,25485093,25483194,25486962,25485197,25484867,25487515,25484746,25483172,25484856,25483251,25485270,25483940,25484933,25486139,25483319,25483569,25483567,25485119,25483299,25484419,25483492,25487771,25487344,25485427,25483746,25486219,25483918,25483240,25483861,25485097,25483176,25483981,25484678,25483225,25483101,25484053,25485734,25485730,25486632,25484042,25486163,25483264,25483156,25487553,25485689,25483149,25484827,25484502,25484036,25484458,25483820,25483461,25486274,25484265,25487655,25486573,25487184,25485247,25483470,25483477,25483468,25486040,25484621,25483227,25483074],\"url\":\"https://www.theguardian.com/lifeandstyle/2014/jul/19/change-your-life-sit-down-and-think\",\"score\":775,\"title\":\"All problems stem from man's inability to sit quietly in a room alone (2014)\",\"parts\":[],\"descendants\":420}").get
  val storyF: Item = ResponseReader.toItem("{\"id\":25466416,\"type\":\"story\",\"by\":\"edent\",\"time\":1608294508,\"kids\":[25466995,25467207,25467272,25467300,25467161,25467318,25467215,25469598,25467463,25467035,25467112,25466953,25468830,25475495,25471367,25468741,25470861,25471291,25468503,25473447,25472167,25468601,25466676,25468276,25468168,25468739,25466875,25473750,25466685,25470878,25469659,25467559,25467250,25469418,25467061,25467968],\"url\":\"https://shkspr.mobi/blog/2020/12/i-have-resigned-from-the-google-amp-advisory-committee/\",\"score\":739,\"title\":\"I Have Resigned from the Google AMP Advisory Committee\",\"parts\":[],\"descendants\":313}").get

  test("testDisplay item exists") {
    val itemService = new ItemService(new MockApiClient(
      items = Array(storyA, storyB, storyC)
    ))
    val item = itemService.display("25430885")
    assert(item.id.toString == "25430885")
  }

  test("testDisplay item doesn't exist") {
    val itemService = new ItemService(new MockApiClient(
      items = Array(storyA, storyC)
    ))
    intercept[NoSuchElementException]{
      itemService.display("25430885")
    }
  }

  test("testDisplayPage first smaller page") {
    val itemService = new ItemService(new MockApiClient(
      items = Array(storyA, storyB, storyC, storyD, storyE, storyF)
    ))
    val items = itemService.displayPage(1, 5, Array("25439878", "25430885", "25445725", "25466304", "25482927", "25466416"))
    assert(items.corresponds(Array(storyA, storyB, storyC, storyD, storyE))((first, second) => first.id == second.id))
  }

  test("testDisplayPage second page, enough stories") {
    val itemService = new ItemService(new MockApiClient(
      items = Array(storyA, storyB, storyC, storyD, storyE, storyF)
    ))
    val items = itemService.displayPage(2, 2, Array("25439878", "25430885", "25445725", "25466304", "25482927", "25466416"))
    assert(items.corresponds(Array(storyC, storyD))((first, second) => first.id == second.id))
  }

  test("testDisplayPage second page, not enough stories") {
    val itemService = new ItemService(new MockApiClient(
      items = Array(storyA, storyB, storyC, storyD, storyE, storyF)
    ))
    val items = itemService.displayPage(2, 4, Array("25439878", "25430885", "25445725", "25466304", "25482927", "25466416"))
    assert(items.corresponds(Array(storyE, storyF))((first, second) => first.id == second.id))
  }

  test("testDisplayIf") {
    val itemService = new ItemService(new MockApiClient(
      items = Array(storyA, storyB, storyC, storyD, storyE, storyF)
    ))
    val items = itemService.displayIf(
      Array("25430885", "25445725", "42", "25466304", "25482927", "25466416"),
      item => Array("25430885", "25445725", "42", "25466304", "25482927").contains(item.id.toString))
    assert(items.corresponds(Array(storyB, storyC, storyD, storyE))((first, second) => first.id == second.id))
  }
}
