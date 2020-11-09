package cz.cvut.fit.bioop.hackernewsclient

case class Logger(className: String) {
  private def logBuilder(severity: String, message: String, color: String): Unit = {
    println(color + "[" + severity + "] " + className + " " + message + Console.RESET)
  }

  def info(message: String): Unit = {
    logBuilder("INFO", message, Console.GREEN)
  }

  def warning(message: String): Unit = {
    logBuilder("WARNING", message, Console.RED)
  }

}