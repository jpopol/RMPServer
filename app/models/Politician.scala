package models

import java.net.URL
import com.github.tototoshi.csv.CSVReader
import play.api.Play.current
import play.api.Play
import play.Logger


case class Politician (
  id: Long, firstname: String, lastname: String, party: String, constituency:String, url:URL
 )

object Politician{
  var politicians :Set[Politician] = Set()
  /*  Politician(20,"Caoimhghín","Ó Caoláin","Sinn Fein","Cavan-Monaghan",new URL("http://www.kildarestreet.com/td/caoimhghin_o_caolain/cavan-monaghan")),
    Politician(2,"Éamon","Ó Cuív","Fianna Fail","Galway West",new URL("http://www.kildarestreet.com/td/eamon_o_cuiv/galway_west")),
    Politician(21,"Seán","Ó Fearghaíl","Fianna Fail","Kildare South",new URL("http://www.kildarestreet.com/td/sean_o_fearghail/kildare_south")),
    Politician(322,"Aodhán","Ó Ríordáin","Labour","Dublin North Central",new URL("http://www.kildarestreet.com/td/aodhan_o_riordain/dublin_north_central"))
  )*/
  val reader = CSVReader.open(Play.getExistingFile("public/resources/dail.csv").get)
  Logger.debug("reader : " + reader.toString)
  politicians = reader.allWithHeaders().map { m =>
    Logger.debug(m.toString)
  //id,firstname,lastname,party,constituency,uri
    Politician(m.get("id").getOrElse("0").toLong,
      m.get("firstname").getOrElse(""),
      m.get("lastname").getOrElse(""),
      m.get("party").getOrElse(""),
      m.get("constituency").getOrElse(""),
      new URL(m.get("uri").getOrElse("")))
  }.toSet

  def findall = politicians.toList.sortBy(_.id)
  def findById(id: Long) = politicians.find(_.id == id)
  def findByConstituency(constituency: String) = politicians.filter(_.constituency == constituency)
  def findByParty(party: String) = politicians.filter(_.party == party)
}