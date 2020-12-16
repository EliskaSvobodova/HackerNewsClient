package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.cache.Cache
import cz.cvut.fit.bioop.hackernewsclient.logger.Logger

object CommandProcessor {
  def process(appOptions: AppOptions, args: Array[String]): Unit = {
    Logger.minSeverity = appOptions.log
    Cache.timeToLive = appOptions.ttl
    if(appOptions.help)
      println(getHelp)
    else if(args.length != 0) {
      val command = CommandFactory.create(appOptions, args)
      command.execute()
    }
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
