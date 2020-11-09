package cz.cvut.fit.bioop.hackernewsclient

object Main {
  def main(args: Array[String]): Unit = {
    val sArgs = splitArgs(args)
    logger.info("Split arguments: app options = " + sArgs._1.mkString("(", ", ", ")")
                             + ", app commands = " + sArgs._2.mkString("(", ", ", ")"))
    val appOptions = parseAppOptions(sArgs._1)
    logger.info("Parsed app options: " + appOptions)
    appOptions.processOptions()
    CommandProcessor.process(appOptions, sArgs._2)
  }

  private val logger = Logger(getClass.getSimpleName)

  /**
   * Reads top application options and creates AppOptions according to them
   *
   * @param args all app's arguments
   * @return filled out AppOptions
   */
  def parseAppOptions(args: Array[String]): AppOptions = {
    for(arg <- args)
      arg match {
          case "--help" => return AppOptions(help = true)
          case _ => throw new IllegalArgumentException("Unknown application option, try --help for possible options")
      }
    AppOptions()
  }

  /**
   * Splits app's arguments to app options and commands with their options
   *
   * @param args app's arguments
   * @return (app options, commands with their options)
   */
  def splitArgs(args: Array[String]): (Array[String], Array[String]) = {
    val splitIndex = args.indexWhere(arg => !arg.startsWith("--"))
    if(splitIndex == -1) // there are no commands
      return (args, Array[String]())
    args.splitAt(splitIndex)
  }
}
