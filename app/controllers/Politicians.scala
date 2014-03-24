package controllers

import play.api.mvc.{Controller, Action}
import controllers.dao.PoliticianDao
import controllers.dao.PoliticianDao

object Politicians extends Controller {

  def list = Action { implicit request =>
    val politicians = PoliticianDao.list.toList
    Ok(views.html.politicians.list(politicians))
  }

  def showPolitician(id: Long) = Action { implicit request =>
      Ok(views.html.politicians.details( PoliticianDao.getById(id).head))

  }

  def showConstituency(constituency: String) = Action { implicit request =>
    val politicians = PoliticianDao.filterByConstituency(constituency)
    politicians match {
      case p:List[_] => if (p.isEmpty) NotFound else Ok(views.html.politicians.constituency(politicians))
      case _ => NotFound
    }
  }

  def showParty(party: String) = Action { implicit request =>
    val politicians = PoliticianDao.filterByParty(party)
    politicians match {
      case p:List[_] => if (p.isEmpty) NotFound else  Ok(views.html.politicians.party(politicians))
    }
  }

}
