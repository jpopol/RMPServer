package models

import java.net.URL
import com.github.tototoshi.csv.CSVReader
import play.api.Play.current
import play.api.Play
import play.Logger
import play.api.libs.json._


case class Politician (
  id: Long, firstname: String, lastname: String, party: String, constituency:String, url: String
 )

object Politician{
  val reader = CSVReader.open(Play.getExistingFile("public/resources/dail.csv").get)
  Logger.debug("reader : " + reader.toString)
  val politicians = reader.allWithHeaders().map { m =>
    Logger.debug(m.toString)
  //id,firstname,lastname,party,constituency,uri
    Politician(m.get("id").getOrElse("0").toLong,
      m.get("firstname").getOrElse(""),
      m.get("lastname").getOrElse(""),
      m.get("party").getOrElse(""),
      m.get("constituency").getOrElse(""),
      m.get("uri").getOrElse(""))
  }.toSet

  def findall = politicians.toList.sortBy(_.id)
  def findById(id: Long) = politicians.find(_.id == id)
  def findByConstituency(constituency: String) = politicians.filter(_.constituency == constituency)
  def findByParty(party: String) = politicians.filter(_.party == party)

  // here to allow conversion to Json
  implicit val politicianFmt = Json.format[Politician]
}