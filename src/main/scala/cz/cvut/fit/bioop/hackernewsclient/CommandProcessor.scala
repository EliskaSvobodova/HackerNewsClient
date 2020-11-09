package cz.cvut.fit.bioop.hackernewsclient

object CommandProcessor {
  def process(appOptions: AppOptions, args: Array[String]): Unit = {
    if(args.length == 0) {
      return ()
    }
    val command = CommandFactory.create(appOptions, args)
    command.execute()
  }
}
