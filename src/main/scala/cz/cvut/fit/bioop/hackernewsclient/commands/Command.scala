package cz.cvut.fit.bioop.hackernewsclient.commands

import cz.cvut.fit.bioop.hackernewsclient.AppOptions

/**
 * Trait for all commands, follows command pattern
 */
trait Command {
  def appOptions: AppOptions
  def commandOptions: Array[String]
  def execute()
}
