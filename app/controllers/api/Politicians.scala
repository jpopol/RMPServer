package controllers.api

import play.Logger
import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import controllers.dao.PoliticianDao

object Politicians extends Controller {
  val MimeType = "application/json"

  def list = Action { implicit request =>
    Logger.debug("Loading shit and stuff");
    Ok(Json.toJson(PoliticianDao.list())).as(MimeType)
  }

  def showPolitician(id: Long) = Action { implicit request =>
    val politician = PoliticianDao.getById(id).head
    Ok(Json.toJson(politician))as(MimeType)

  }

  def showConstituency(constituency: String) = Action { implicit request =>
    val politicians = PoliticianDao.filterByConstituency(constituency)
    politicians match {
      case p:List[_] => if (p.isEmpty) NotFound else Ok(Json.toJson(politicians))as(MimeType)
      case _ => NotFound
    }
  }

  def showParty(party: String) =Action { implicit request =>
    val politicians = PoliticianDao.filterByParty(party)
    politicians match {
      case p:List[_] => if (p.isEmpty) NotFound else  Ok(Json.toJson(politicians))as(MimeType)
    }
  }
}
