package cz.cvut.fit.bioop.hackernewsclient.cache.locations

/**
 * Handles writing and reading cache data from their location
 */
trait CacheLocation {
  def getLines: Iterable[String]

  def write(lines: Iterable[String]): Unit
  def append(lines: Iterable[String]): Unit

  def write(line: String): Unit
  def append(line: String): Unit

  def clearCache(): Unit
}