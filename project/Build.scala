import sbt._
import sbt.Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "RMPServer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.github.tototoshi" %% "scala-csv" % "1.0.0-SNAPSHOT",
    "org.webjars" % "bootstrap" % "3.0.3",
    "org.webjars" % "flat-ui" % "bcaf2de95e"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    //Sonatype repository
    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
  )

}
