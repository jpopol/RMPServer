import com.github.tototoshi.csv.CSVReader
import models.{Politician, Politicians}
import play.api.{Mode, Play, GlobalSettings, Application}
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import play.Logger


// Use H2Driver to connect to an H2 database
//import scala.slick.driver.H2Driver.simple._
// Use the implicit threadLocalSession
//import Database.threadLocalSession

import play.api.Play.current

object Global extends GlobalSettings {
  lazy val politicians = TableQuery[Politicians]

  override def onStart(app: Application) {
    super.onStart(app)

    DB withSession {
      implicit session:Session =>
        Logger.debug("Creating tables")
        //politicians.ddl.create
        
        val reader = if(app.mode != Mode.Prod){
          CSVReader.open(Play.getExistingFile("public/dail.csv").get)
        } else {
          CSVReader.open("/app/public/dail.csv")
        }
        Logger.debug("reader : " + reader.toString)
        try {
          reader.allWithHeaders().foreach { m =>
            if(app.mode != Mode.Test){
              Logger.debug(m.toString)
            }
            //id,firstname,lastname,party,constituency,uri
            politicians += Politician(m.get("id").getOrElse("0").toLong,
              m.get("firstname").getOrElse(""),
              m.get("lastname").getOrElse(""),
              m.get("party").getOrElse(""),
              m.get("constituency").getOrElse(""))
          }
        } catch {
          case _ :Throwable => Logger.warn("Can't populate database")
        }
    }
  }


  override def onStop(app: Application): Unit = {
    Logger.info("Shutting down the server")
    super.onStop(app)
  }




}

