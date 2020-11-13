package cz.cvut.fit.bioop.hackernewsclient

case class AppOptions(help: Boolean = false, limitOfStories: Int = 10) {
  override def toString: String = {
    val fields = for {
      field <- getClass.getDeclaredFields
    } yield field.getName + " = " + field.get(this)
    fields.mkString("AppOptions[", ", ", "]")
  }

  def processOptions() = {
    if(help){
      println("Very helpful help")
      for(f <- getClass.getDeclaredFields)  yield f.toString
    }
  }
}
