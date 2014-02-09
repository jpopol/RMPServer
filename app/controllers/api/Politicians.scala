package controllers.api

import play.api.mvc.{Action, Controller}
import models.{Politicians, Politician}
import play.api.libs.json._

import scala.slick.driver.H2Driver.simple._
import play.db.DB
import controllers.dao.PoliticianDao


object Politicians extends Controller {
  val MimeType = "application/json"

  def list = Action { implicit request =>
    Ok(Json.toJson(PoliticianDao.list())).as(MimeType)
  }

  def showPolitician(id: Long) =TODO/* Action { implicit request =>
    Politician.findById(id).map{ politician =>
      Ok(Json.toJson(politician))as(MimeType)
    }.getOrElse(NotFound)
  }*/

  def showConstituency(constituency: String) =TODO /*Action { implicit request =>
    val politicians = Politician.findByConstituency(constituency)
    politicians match {
      case p:Set[_] => if (p.isEmpty) NotFound else Ok(Json.toJson(politicians))as(MimeType)
      case _ => NotFound
    }
  }*/

  def showParty(party: String) =TODO /*Action { implicit request =>
    val politicians = Politician.findByParty(party)
    politicians match {
      case p:Set[_] => if (p.isEmpty) NotFound else  Ok(Json.toJson(politicians))as(MimeType)
    }
  }*/
}
