package cz.cvut.fit.bioop.hackernewsclient

object AppOptions {
  /**
   * Reads top application options and creates AppOptions according to them
   *
   * @param args all app's arguments
   * @return filled out AppOptions
   */
  def parseAppOptions(args: Array[String]): AppOptions = {
    val appOptions = AppOptions()
    for(arg <- args)
      arg match {
        case "--help" => appOptions.help = true
        case _ => throw new IllegalArgumentException("Unknown application option, try --help for possible options")
      }
    appOptions
  }

  def getHelp(): String = {
    var help = "[APPLICATION OPTIONS]\n"
    help += optionHelpBuilder("help", "displays help")
    help += optionHelpBuilder("limitOfStories=[value]", "how many stories should be displayed at once")
    help
  }

  private def optionHelpBuilder(option: String, descr: String): String = {
    option + " .. " + descr + "\n"
  }
}

case class AppOptions(var help: Boolean = false, var limitOfStories: Int = 10) {
  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("AppOptions[", ", ", "]")
  }
}
