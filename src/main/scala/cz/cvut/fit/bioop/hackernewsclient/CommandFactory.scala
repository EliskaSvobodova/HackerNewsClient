package cz.cvut.fit.bioop.hackernewsclient

import cz.cvut.fit.bioop.hackernewsclient.commands.stories.{BestStoriesCommand, NewStoriesCommand, TopStoriesCommand}
import cz.cvut.fit.bioop.hackernewsclient.commands.{Command, CommandObject, CommentsCommand, NotFoundCommand, UserCommand}

/**
 * Creates Command which is requested in app options
 */
object CommandFactory {
  val allCommands: Array[CommandObject] = Array(TopStoriesCommand, NewStoriesCommand, BestStoriesCommand,
                                                UserCommand, CommentsCommand)

  def create(appOptions: AppOptions, args: Array[String]): Command = {
    val commandOptions = args.slice(1, args.length)
    if(args.length == 0)
      return new NotFoundCommand(appOptions, commandOptions)
    args(0) match {
      case TopStoriesCommand.name   => new TopStoriesCommand(appOptions, commandOptions)
      case NewStoriesCommand.name   => new NewStoriesCommand(appOptions, commandOptions)
      case BestStoriesCommand.name  => new BestStoriesCommand(appOptions, commandOptions)
      case UserCommand.name         => new UserCommand(appOptions, commandOptions)
      case CommentsCommand.name     => new CommentsCommand(appOptions, commandOptions)
      case _                        => new NotFoundCommand(appOptions, commandOptions)
    }
  }
}
