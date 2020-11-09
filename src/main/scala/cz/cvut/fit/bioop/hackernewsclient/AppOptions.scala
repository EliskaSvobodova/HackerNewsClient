package cz.cvut.fit.bioop.hackernewsclient

case class AppOptions(help: Boolean = false) {
  override def toString: String = "AppOptions[help = " + help + "]"

  def processOptions() = {
    if(help){
      println("Very helpful help")
    }
  }
}
