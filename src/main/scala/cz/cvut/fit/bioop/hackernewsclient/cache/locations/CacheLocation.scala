package cz.cvut.fit.bioop.hackernewsclient.cache.locations

trait CacheLocation {
  def getLines: Iterator[String]

  def write(lines: Iterable[String]): Unit
  def append(lines: Iterable[String]): Unit

  def write(line: String): Unit
  def append(line: String): Unit

  def clearCache(): Unit
}