package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}

case class Updates(items: Array[String], profiles: Array[String]) {
  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("Updates[", ", ", "]")
  }
}

object Updates{
  implicit val rw: RW[Updates] = macroRW
}