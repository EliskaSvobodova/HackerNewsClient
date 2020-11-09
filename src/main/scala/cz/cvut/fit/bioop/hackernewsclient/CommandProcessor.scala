package cz.cvut.fit.bioop.hackernewsclient

object CommandProcessor {
  def process(appOptions: AppOptions, args: Array[String]) = {
    val command = CommandFactory.create(appOptions, args)
    command.execute()
  }
}
