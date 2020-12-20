package cz.cvut.fit.bioop.hackernewsclient

object CommandProcessor {
  def process(appOptions: AppOptions, args: Array[String]): Unit = {
    appOptions.process()
    if(appOptions.shouldContinue())
      CommandFactory.create(appOptions, args).execute()
  }

  def getHelp: String = {
    var help = "This is a command line client for the well-known news website Hacker News\n"
    help += "Run it like this: hackernewsclient --[options] [command] --[command-options]\n\n"
    help += AppOptions.getHelp
    help += "\n"
    for(command <- CommandFactory.allCommands) {
      help += command.name + ": \n" + "------------------------\n" + command.help()
    }
    help
  }
}
