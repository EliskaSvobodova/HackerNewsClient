package cz.cvut.fit.bioop.hackernewsclient

object Main {
  def main(args: Array[String]): Unit = {
    val sArgs = argsSplit(args)
    val appOptions = AppOptions(sArgs._1)
    CommandProcessor.process(appOptions, sArgs._2)
  }

  /**
   * Divides program arguments to app options and command with command options
   * @param args program arguments
   * @return two arrays: (app options, command with command options)
   */
  def argsSplit(args: Array[String]): (Array[String], Array[String]) = {
    val splitIndex = args.indexWhere(arg => !arg.startsWith("--"))
    if(splitIndex == -1) // there are no commands
      return (args, Array[String]())
    args.splitAt(splitIndex)
  }
}
