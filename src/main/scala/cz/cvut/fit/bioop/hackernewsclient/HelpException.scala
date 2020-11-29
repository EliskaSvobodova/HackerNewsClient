package cz.cvut.fit.bioop.hackernewsclient

final case class HelpException(private val message: String = "", private val cause: Throwable = None.orNull)
  extends Exception(message, cause)