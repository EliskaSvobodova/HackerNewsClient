package cz.cvut.fit.bioop.hackernewsclient

object Main {
  def main(args: Array[String]): Unit = {
    val sArgs = appOptionsSplit(args)
    logger.info("Split arguments: app options = " + sArgs._1.mkString("(", ", ", ")")
                             + ", app commands = " + sArgs._2.mkString("(", ", ", ")"))
    val appOptions = AppOptions.parseAppOptions(sArgs._1)
    logger.info("Parsed app options: " + appOptions)
    CommandProcessor.process(appOptions, sArgs._2)
  }

  private val logger = Logger(getClass.getSimpleName)

  private def appOptionsSplit(args: Array[String]): (Array[String], Array[String]) = {
    val splitIndex = args.indexWhere(arg => !arg.startsWith("--"))
    if(splitIndex == -1) // there are no commands
      return (args, Array[String]())
    args.splitAt(splitIndex)
  }
}
