package cz.cvut.fit.bioop.hackernewsclient.api.apiObjects

import upickle.default.{macroRW, ReadWriter => RW}

/**
 * Data class that holds info about a user from web api
 * @param id The user's unique username. Case-sensitive. Required.
 * @param created Creation date of the user, in Unix Time.
 * @param karma The user's karma.
 * @param about The user's optional self-description. HTML.
 * @param submitted List of the user's stories, polls and comments.
 */
case class User (id: String = "noUserID",
                 created: Long = -1,
                 karma: Long = 0,
                 about: String = "",
                 submitted: Array[Long] = Array()) {

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