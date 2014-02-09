import com.github.tototoshi.csv.CSVReader
import models.{Politician, Politicians}
import play.api.db.DB
import play.api.{Mode, Play, GlobalSettings, Application}
import play.Logger

// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._
// Use the implicit threadLocalSession
//import Database.threadLocalSession

import play.api.Play.current

object Global extends GlobalSettings {
  lazy val database = Database.forDataSource(DB.getDataSource())
  val politicians = TableQuery[Politicians]

  override def onStart(app: Application) {

    database.withSession {
      implicit session:Session =>
        Logger.debug("Creating tables")
        politicians.ddl.create
        //TODO: Remove when on a real DB

        val reader = if(app.mode != Mode.Prod){
          CSVReader.open(Play.getExistingFile("public/dail.csv").get)
        } else {
          CSVReader.open("/app/public/dail.csv")
        }
        Logger.debug("reader : " + reader.toString)
        reader.allWithHeaders().foreach { m =>
          Logger.debug(m.toString)
          //id,firstname,lastname,party,constituency,uri
          politicians += Politician(m.get("id").getOrElse("0").toLong,
            m.get("firstname").getOrElse(""),
            m.get("lastname").getOrElse(""),
            m.get("party").getOrElse(""),
            m.get("constituency").getOrElse(""),
            m.get("uri").getOrElse(""))
        }

    }
    super.onStart(app)
  }

  override def onStop(app: Application): Unit = {

    super.onStop(app)

    database.withSession {
      implicit session:Session =>
        Logger.debug("Droping tables")
        politicians.ddl.drop
    }
  }



}

