import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "RMPServer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "com.github.tototoshi" %% "scala-csv" % "1.0.0-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    //Sonatype repository
    resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
  )

}
