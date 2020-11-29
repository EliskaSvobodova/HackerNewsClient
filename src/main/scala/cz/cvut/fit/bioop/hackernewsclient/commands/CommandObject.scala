package cz.cvut.fit.bioop.hackernewsclient.commands

trait CommandObject {
  val name: String
  def help(): String

  protected def buildHelp(name: String, description: String, options: Map[String, String]): String = {
    var help = "[" + name + "]" + "\n" + description + "\n"
    for(opt <- options){
      help = help + opt._1 + " ... " + opt._2 + "\n"
    }
    help + "\n"
  }
}