package cz.cvut.fit.bioop.hackernewsclient

object Main {
  def main(args: Array[String]): Unit = {
    val appOptions = parseAppOptions(args)
    println(appOptions)
  }

  private val optionPattern = "--(.*)".r

  /**
   * Reads top application options and creates AppOptions according to them
   *
   * @param args all program's arguments
   * @return filled out AppOptions
   */
  def parseAppOptions(args: Array[String]): AppOptions = {
    for(arg <- args)
      arg match {
        case optionPattern(arg) =>
          arg match {
            case "help" => return AppOptions(help = true)
            case _ => throw new IllegalArgumentException("Unknown application option, try --help for possible options")
          }
        case _ => return AppOptions()
      }
    AppOptions()
  }
}
