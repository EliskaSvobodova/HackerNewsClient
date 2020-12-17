package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}

case class Updates(items: Array[String], profiles: Array[String]) {
  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("Updates[", ", ", "]")
  }

  def sameUpdates(other: Updates): Boolean = {
    items.sameElements(other.items) && profiles.sameElements(other.profiles)
  }
}

object Updates{
  implicit val rw: RW[Updates] = macroRW
}