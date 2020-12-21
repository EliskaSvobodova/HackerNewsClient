package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.cache.Cache
import cz.cvut.fit.bioop.hackernewsclient.logger.{Logger, LoggerSeverity}

object AppOptions {
  /**
   * Reads top application options and creates AppOptions according to them
   *
   * @param args all app's arguments
   * @return filled out AppOptions
   */
  def apply(args: Array[String]): AppOptions = {
    val appOptions = new AppOptions()
    for(arg <- args)
      arg match {
        case "--help" => appOptions.help = true
        case Logger.severityRe(severity) => appOptions.log = LoggerSeverity.withName(severity)
        case Cache.cacheTtlRe(numSec) => appOptions.ttl = numSec.toLong
        case "--clear-cache" => appOptions.clearCache = true
        case _ => throw new IllegalArgumentException("Unknown application option, try --help for possible options")
      }
    appOptions
  }

  def getHelp: String = {
    var help = "[APPLICATION OPTIONS]\n"
    help += optionHelpBuilder("help", "displays help")
    help += optionHelpBuilder("log", "what message severity should be logged")
    help += optionHelpBuilder("ttl=[value]", "for how long should be elements stored in cache in seconds")
    help += optionHelpBuilder("--clear-cache", "deletes all cached data")
    help
  }

  private def optionHelpBuilder(option: String, descr: String): String = {
    option + " .. " + descr + "\n"
  }
}

/**
 * Data class that holds application options
 * @param help should be help displayed
 * @param log what min severity of logging should be displayed
 * @param ttl time to live for cached elements in seconds
 * @param clearCache should be all data removed from the cache
 */
case class AppOptions(var help: Boolean = false,
                      var log: LoggerSeverity.Value = Logger.defaultMinSeverity,
                      var ttl: Long = Cache.defaultTimeToLive,
                      var clearCache: Boolean = false) {
  override def toString: String = {
    val fields = for { field <- getClass.getDeclaredFields } yield field.getName + " = " + field.get(this)
    fields.mkString("AppOptions[", ", ", "]")
  }

  /**
   * Should be commands executed or should the application exit
   */
  def shouldContinue(): Boolean = {
    !help
  }

  /**
   * Handle all necessary tasks according to given application options
   */
  def process(): Unit = {
    Logger.minSeverity = log
    if(help) {
      println(CommandProcessor.getHelp)
    } else if (clearCache)
      Cache.clearCache()
  }
}
