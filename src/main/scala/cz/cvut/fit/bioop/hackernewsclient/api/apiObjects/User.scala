package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}

case class User (id: String = "noUserID",
                 delay: Long = -1,
                 created: Long = -1,
                 karma: Long = 0,
                 about: String = "",
                 submitted: Array[String] = Array()) {

  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("User[", ", ", "]")
  }
}

object User{
  implicit val rw: RW[User] = macroRW
}