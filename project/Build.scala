import sbt._
import sbt.Keys._
import play.Project._
import net.litola.SassPlugin


object ApplicationBuild extends Build {

  val appName         = "RMPServer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaJdbc,
    cache,
    "com.typesafe.slick" %% "slick" % "2.0.0",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.typesafe.play" %% "play-slick" % "0.6.0.1",
    "com.github.tototoshi" %% "scala-csv" % "1.0.0",
    "org.webjars" %% "webjars-play" % "2.2.1",
    "org.webjars" % "foundation" % "5.1.1"
  )


  //play.Project.playScalaSettings ++ SassPlugin.sassSettings
  val main = play.Project(appName, appVersion, appDependencies).settings(SassPlugin.sassSettings:_*).settings(
    // Add your own project settings here
    //Sonatype repository
    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
  )

}
