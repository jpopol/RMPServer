package models

import play.api.db.DB
// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._
import play.api.Play.current


object RmpDb {

  def database = Database.forDataSource(DB.getDataSource())

}
