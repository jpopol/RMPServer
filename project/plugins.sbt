// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.1")

//WebJar resolver
resolvers += "webjars" at "http://webjars.github.com/m2"

//sass compiler
//addSbtPlugin("net.litola" % "play-sass" % "0.3.0")
