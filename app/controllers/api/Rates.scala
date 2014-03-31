package controllers.api

import play.api.mvc.{Controller, Action}
import play.api.libs.json._
import controllers.dao.{RateDao, PoliticianDao}
import play.Logger

/**
 * Created with IntelliJ IDEA.
 * User: ejeabon
 * Date: 3/25/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
object Rates extends Controller {
  val MimeType = "application/json"

  def getRates(politicianId: Long) = Action { implicit request =>
    //TODO handle a bad politician
    val politician = PoliticianDao.getById(politicianId).head
    if(politician == null)NotFound(politicianId.asInstanceOf[String])

    val rates = RateDao.getAll(politician)
    Ok(Json.toJson(rates))as(MimeType)
  }

  def addRate(politicianId: Long) = Action { implicit request =>
    //TODO handle a bad politician
    val politician =  PoliticianDao.getById(politicianId).head
    val partialRate = request.body.asJson
    Logger.debug(f"received data for politician: $politician - $partialRate")
    //partialRate.
    Ok("return id")
  }

}
