package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.AppOptions

trait Command {
  def appOptions: AppOptions
  def commandOptions: Array[String]
  def execute()

  protected def printUnknownOption(option: String, commandName: String): Unit = {
    println(commandName + " - unknown option \"" + option + "\"")
    println("Try \"hackernewsclient " + commandName + " --help\" for possible options")
  }
}
