package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.commands.CommandObject

import scala.collection.immutable.ListMap

trait StoriesCommandObject extends CommandObject {
  protected val helpOptions: Map[String, String] = ListMap(
    "--page=[value]" -> "which page should be displayed",
    "--page-size=[value]" -> "how many stories are on one page",
    "--help" -> "display help"
  )
}