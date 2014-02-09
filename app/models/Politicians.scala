package models

import scala.slick.driver.H2Driver.simple._
import play.api.libs.json.Json


class Politicians(tag: Tag) extends Table[Politician](tag,"Politician") {

  def id = column[Long]("ID", O.PrimaryKey)
  def firstname = column[String]("Firstname")
  def lastname = column[String]("Lastname")
  def party = column[String]("Party")
  def constituency = column[String]("Constituency")
  def url = column[String]("Url")
  def * = (id, firstname, lastname, party, constituency, url) <> (Politician.tupled, Politician.unapply _)

}

case class Politician (val id: Long, val firstname: String, val lastname: String, val party: String, val constituency:String, val url: String)

object Politician extends ((Long, String, String, String, String, String) => Politician) {
  implicit val politicianFmt = Json.format[Politician]
  Politician.tupled
}

//TODO: Remove
/*
def findall = politicians.toList.sortBy(_.id)
def findById(id: Long) = politicians.find(_.id == id)
def findByConstituency(constituency: String) = politicians.filter(_.constituency == constituency)
def findByParty(party: String) = politicians.filter(_.party == party)
*/


