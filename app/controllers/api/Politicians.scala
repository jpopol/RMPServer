package controllers.api

import play.api.mvc.{Action, Controller}
import models.Politician
import play.api.libs.json._

object Politicians extends Controller {
  val MimeType = "application/json"

  def list = Action { implicit request =>
    val politicians = Politician.findall
    Ok(Json.toJson(politicians)).as(MimeType)
  }

  def showPolitician(id: Long) = Action { implicit request =>
    Politician.findById(id).map{ politician =>
      Ok(Json.toJson(politician))as(MimeType)
    }.getOrElse(NotFound)
  }

  def showConstituency(constituency: String) = Action { implicit request =>
    val politicians = Politician.findByConstituency(constituency)
    politicians match {
      case p:Set[_] => if (p.isEmpty) NotFound else Ok(Json.toJson(politicians))as(MimeType)
      case _ => NotFound
    }
  }

  def showParty(party: String) = Action { implicit request =>
    val politicians = Politician.findByParty(party)
    politicians match {
      case p:Set[_] => if (p.isEmpty) NotFound else  Ok(Json.toJson(politicians))as(MimeType)
    }
  }
}
