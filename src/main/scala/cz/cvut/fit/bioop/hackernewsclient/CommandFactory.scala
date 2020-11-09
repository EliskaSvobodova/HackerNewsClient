package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.commands.{Command, NotFoundCommand, TopStoriesCommand}

object CommandFactory {
  def create(appOptions: AppOptions, args: Array[String]): Command = {
    val commandOptions = args.slice(1, args.length)
    args[0] match {
      case "top-stories" => new TopStoriesCommand(appOptions, commandOptions)
      case notFoundName => new NotFoundCommand(notFoundName + "-command-not-found", appOptions, commandOptions)
    }
  }

}
