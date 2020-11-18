package cz.cvut.fit.bioop.hackernewsclient

object ArgsSplitter {
  /**
   * Splits app's arguments to app options and commands with their options
   *
   * @param args app's arguments
   * @return (app options, commands with their options)
   */
  def appOptionsSplit(args: Array[String]): (Array[String], Array[String]) = {
    val splitIndex = args.indexWhere(arg => !arg.startsWith("--"))
    if(splitIndex == -1) // there are no commands
      return (args, Array[String]())
    args.splitAt(splitIndex)
  }

  def commandSplit(args: Array[String]): (Array[String], Array[String]) = {
    val splitIndex = args.indexWhere(arg => arg.startsWith("--"))
    if(splitIndex == -1) // there are no command options
      return (args, Array[String]())
    args.splitAt(splitIndex)
  }
}
