package cz.cvut.fit.bioop.hackernewsclient.cache.locations

import scala.collection.mutable.ArrayBuffer

/**
 * Mock cache location that stores data in array
 * @param elems elements that are currently in "cache"
 */
class MockLocation(var elems: ArrayBuffer[String] = ArrayBuffer()) extends CacheLocation {
  override def getLines: Iterable[String] = elems

  override def write(lines: Iterable[String]): Unit = elems = ArrayBuffer().appendAll(lines)

  override def append(lines: Iterable[String]): Unit = elems.appendAll(lines)

  override def write(line: String): Unit = elems = ArrayBuffer(line)

  override def append(line: String): Unit = elems.append(line)

  override def clearCache(): Unit = elems = ArrayBuffer()
}
