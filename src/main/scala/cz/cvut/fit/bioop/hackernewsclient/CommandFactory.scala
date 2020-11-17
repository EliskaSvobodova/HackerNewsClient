package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.commands.{Command, CommandObject, NotFoundCommand, TopStoriesCommand}

object CommandFactory {
  val allCommands: Array[CommandObject] = Array(TopStoriesCommand)

  def create(appOptions: AppOptions, args: Array[String]): Command = {
    val commandOptions = args.slice(1, args.length)
    args(0) match {
      case TopStoriesCommand.name => new TopStoriesCommand(appOptions, commandOptions)
      case _ => new NotFoundCommand(appOptions, commandOptions)
    }
  }
}
