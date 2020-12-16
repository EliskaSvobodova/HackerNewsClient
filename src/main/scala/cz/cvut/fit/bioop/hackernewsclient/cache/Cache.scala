package cz.cvut.fit.bioop.hackernewsclient.cache

import cz.cvut.fit.bioop.hackernewsclient.api.apiObjects.{Item, User}

import scala.util.matching.Regex

trait Cache {
  def timeToLive: Long

  def getItem(id: Long): Option[Item]

  def getUser(id: String): Option[User]

  def cacheItem(item: Item): Unit

  def cacheUser(user: User): Unit
}

object Cache {
  val defaultTimeToLive: Long = 5 * 60  // in seconds
  val cacheTtlRe: Regex = "--ttl=([0-9]+)".r
}