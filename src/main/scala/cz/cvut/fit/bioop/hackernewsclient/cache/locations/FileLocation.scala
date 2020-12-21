package cz.cvut.fit.bioop.hackernewsclient.cache.locations
import java.io.{File, FileWriter}
import scala.io.Source

/**
 * Stores cache data in a file
 * @param filename name of the file where to store data
 */
class FileLocation(val filename: String = "default") extends CacheLocation {
  private val cachePath = "cache/"

  override def getLines: Iterable[String] = {
    val source = Source.fromFile(getFile)
    val lines = source.getLines().toArray
    source.close()
    lines
  }

  override def write(lines: Iterable[String]): Unit = {
    writeLines(new FileWriter(getFile), lines)
  }

  override def append(lines: Iterable[String]): Unit = {
    writeLines(new FileWriter(getFile, true), lines)
  }

  override def write(line: String): Unit = {
    writeLines(new FileWriter(getFile), Array(line))
  }

  override def append(line: String): Unit = {
    writeLines(new FileWriter(getFile, true), Array(line))
  }

  private def writeLines(writer: FileWriter, lines: Iterable[String]): Unit = {
    for(line <- lines)
      writer.append(line + '\n')
    writer.close()
  }

  private def getFile: File = {
    val directory = new File(cachePath)
    directory.mkdir()  // create cache directory if it doesn't exist
    val cacheFile = new File(cachePath + filename)
    if(!cacheFile.exists())
      cacheFile.createNewFile()
    cacheFile
  }

  override def clearCache(): Unit = {
    new File(cachePath + filename).delete()
  }
}
