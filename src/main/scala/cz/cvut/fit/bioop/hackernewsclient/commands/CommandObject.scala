package cz.cvut.fit.bioop.hackernewsclient.commands

/**
 * Command object has to be implemented for every command class and has to contain
 * command's name and help
 */
trait CommandObject {
  val name: String
  def help(): String

  /**
   * helper method for commands's help construction
   * @param name name of the command
   * @param description description of the command
   * @param options options with effects of the command
   * @return constructed help string
   */
  protected def buildHelp(name: String, description: String, options: Map[String, String]): String = {
    var help = "[" + name + "]" + "\n" + description + "\n"
    for(opt <- options){
      help = help + opt._1 + " ... " + opt._2 + "\n"
    }
    help + "\n"
  }
}