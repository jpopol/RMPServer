package controllers

import models.Politician
import play.api.mvc.{Controller, Action}

object Politicians extends Controller {

  def list = Action { implicit request =>
    val politicians = Politician.findall

    Ok(views.html.politicians.list(politicians))
  }

  def show(id: Long) = Action { implicit request =>
    Politician.findById(id).map{ politician =>
      Ok(views.html.politicians.details(politician))
    }.getOrElse(NotFound)
  }

  def showConstituency(constituency: String) = Action { implicit request =>
    val politicians = Politician.findByConstituency(constituency)
    politicians match {
      case p:Set[_] => if (p.isEmpty) NotFound else Ok(views.html.politicians.constituency(politicians))
      case _ => NotFound
    }
  }

  def showParty(party: String) = Action { implicit request =>
    val politicians = Politician.findByParty(party)
    politicians match {
      case p:Set[_] => if (p.isEmpty) NotFound else  Ok(views.html.politicians.party(politicians))
    }
  }

}
