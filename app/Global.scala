import com.github.tototoshi.csv.CSVReader
import models.{Politician, Politicians}
import play.api.{Mode, Play, GlobalSettings, Application}
import play.Logger
import models.RmpDb._

// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._
// Use the implicit threadLocalSession
//import Database.threadLocalSession

import play.api.Play.current

object Global extends GlobalSettings {
  val politicians = TableQuery[Politicians]

  override def onStart(app: Application) {
    super.onStart(app)

    database withSession {
      implicit session:Session =>
        //Logger.debug("Creating tables")
        //politicians.ddl.create

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
  }

  override def onStop(app: Application): Unit = {

    database withSession {
      implicit session:Session =>
        //Logger.debug("Dropping tables")
        //politicians.ddl.drop
    }
    super.onStop(app)
  }



}

