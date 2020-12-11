package cz.cvut.fit.bioop.hackernewsclient

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
        case _ => throw new IllegalArgumentException("Unknown application option, try --help for possible options")
      }
    appOptions
  }

  def getHelp: String = {
    var help = "[APPLICATION OPTIONS]\n"
    help += optionHelpBuilder("help", "displays help")
    help += optionHelpBuilder("log", "what message severity should be logged")
    help
  }

  private def optionHelpBuilder(option: String, descr: String): String = {
    option + " .. " + descr + "\n"
  }
}

case class AppOptions(var help: Boolean = false, var log: LoggerSeverity.Value = LoggerSeverity.info) {
  override def toString: String = {
    val fields = for { field <- getClass.getDeclaredFields } yield field.getName + " = " + field.get(this)
    fields.mkString("AppOptions[", ", ", "]")
  }
}
