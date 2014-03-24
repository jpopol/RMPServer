package models

import scala.slick.driver.H2Driver.simple._
import play.api.libs.json.Json


class Politicians(tag: Tag) extends Table[Politician](tag,"Politician") {

  def id = column[Long]("ID", O.PrimaryKey, O.NotNull)
  def firstname = column[String]("Firstname")
  def lastname = column[String]("Lastname")
  def party = column[String]("Party")
  def constituency = column[String]("Constituency")
  def * = (id, firstname, lastname, party, constituency) <> (Politician.tupled, Politician.unapply _)

}

case class Politician (val id: Long, val firstname: String, val lastname: String, val party: String, val constituency:String)

object Politician extends ((Long, String, String, String, String) => Politician) {
  implicit val politicianFmt = Json.format[Politician]
  Politician.tupled
}


