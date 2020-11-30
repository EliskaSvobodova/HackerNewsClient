package cz.cvut.fit.bioop.hackernewsclient.commands.stories

import cz.cvut.fit.bioop.hackernewsclient.commands.Command
import cz.cvut.fit.bioop.hackernewsclient.{HelpException, Logger}

import scala.util.matching.Regex

trait StoriesCommand extends Command {

  private val pageRe: Regex = "--page=([0-9]+)".r
  private val pageSizeRe: Regex = "--page-size=([0-9]+)".r

  // default values, can change according to command options
  private val logger = Logger(getClass.getSimpleName)

  case class Options(page: Int, pageSize: Int)

  def getOptions: Options = {
    var page = 1
    var pageSize = 10

    for(option <- commandOptions){
      option match {
        case pageRe(pageNum) => page = pageNum.toInt
        case pageSizeRe(pageSizeNum) => pageSize = pageSizeNum.toInt
        case "--help" =>
          logger.info("Displaying help for command")
          throw new HelpException
        case unknown =>
          logger.error("\"Unknown option \"" + unknown + "\"")
          throw new IllegalArgumentException("Unknown option \"" + unknown + "\", " +
            "try hackernewsclient --help for possible options")
      }
    }

    Options(page, pageSize)
  }
}
