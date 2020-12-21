package cz.cvut.fit.bioop.hackernewsclient.logger

import cz.cvut.fit.bioop.hackernewsclient.logger.Logger.minSeverity

import scala.util.matching.Regex

/**
 * Application's logger, which min severity to show can be specified as app option
 */
object Logger {
  val severityRe: Regex = "--log=(error|warning|info)$".r
  val defaultMinSeverity: LoggerSeverity.Value = LoggerSeverity.info
  var minSeverity: LoggerSeverity.Value = defaultMinSeverity
}

case class Logger(className: String) {
  private def logBuilder(severity: LoggerSeverity.Value, message: String, color: String): Unit = {
    if(severity <= minSeverity)
      println(color + "[" + severity.toString + "] " + className + " " + message + Console.RESET)
  }

  def info(message: String): Unit = {
    logBuilder(LoggerSeverity.info, message, Console.GREEN)
  }

  def warning(message: String): Unit = {
    logBuilder(LoggerSeverity.warning, message, Console.YELLOW)
  }

  def error(message: String): Unit = {
    logBuilder(LoggerSeverity.error, message, Console.RED)
  }
}