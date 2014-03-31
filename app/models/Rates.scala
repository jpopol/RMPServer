package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json.Json

/**
 * Created with IntelliJ IDEA.
 * User: ejeabon
 * Date: 3/25/14
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */

class Rates (tag: Tag) extends Table[Rate](tag, "Rate") {
  def id = column[Long]("Rate_ID", O.PrimaryKey, O.AutoInc)
  def time = column[Long]("Time")
  def rate = column[Int]("Rate")
  def polID = column[Long]("Pol_ID")
  def politician = foreignKey("Pol_FK", polID, TableQuery[Politicians])(_.id)
  def * = (id.?, time, rate, polID) <> (Rate.tupled, Rate.unapply)
}


case class Rate(val id:Option[Long], val time:Long, val rate: Int, var polID: Long)

object Rate extends((Option[Long], Long, Int, Long) => Rate) {
  implicit val rateFmt = Json.format[Rate]
  Rate.tupled
}

