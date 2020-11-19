package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.api.ApiClient

trait StoriesCommand extends Command {
  protected val pageRe = "--page=([0-9]+)".r

  def printTitles(storiesIds: Array[Int]): Unit = {
    val range = Range(1, appOptions.limitOfStories + 1)
    for {
      (numDisplayed, storyId) <- range zip storiesIds
    } yield {
      val item = ApiClient.getItem(storyId)
      println(numDisplayed + ". " + item.title)
    }
  }

  def printPage(pageNum: Int, storiesIds: Array[Int]): Unit = {
    if(pageNum < 1 || pageNum > storiesIds.length)
      throw new IllegalArgumentException("Page number must be between 1 and " + storiesIds.length)
    val item = ApiClient.getItem(storiesIds(pageNum - 1))
    println(item)
  }
}
