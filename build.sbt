name := "HackerNewsClient"
organization := "cz.cvut.fit.bioop"
version := "1.0"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"
libraryDependencies += "org.mockito" % "mockito-scala_2.13" % "1.6.2" % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

// JSON parsing library
libraryDependencies += "com.lihaoyi" %% "upickle" % "0.9.5"

assemblyJarName in assembly := "HackerNewsClient.jar"
