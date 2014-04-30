package controllers.api

import play.api.mvc.{Controller, Action}
import play.api.libs.json._
import controllers.dao.{RateDao, PoliticianDao}
import play.Logger
import play.api.mvc.BodyParsers
import views.html.defaultpages.badRequest

/**
 * Created with IntelliJ IDEA.
 * User: ejeabon
 * Date: 3/25/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
case class RateJson(politicianId: Long, rating: Int, timestamp: Long)
object RateJson {
  implicit val rateFmt = Json.format[RateJson]
}

object Rates extends Controller {
  val MimeType = "application/json"

  def getRates(politicianId: Long) = Action { implicit request =>
    //TODO handle a bad politician
    val politician = PoliticianDao.getById(politicianId).head
    if(politician == null)NotFound(politicianId.asInstanceOf[String])

    val rates = RateDao.getAll(politician)
    Ok(Json.toJson(rates))as(MimeType)
  }

  def addRate = Action(BodyParsers.parse.json) { implicit request =>
    //TODO handle a bad politician
    val partialRate = request.body.validate[RateJson]
    partialRate.fold(
    errors => {
      Logger.debug("fucked up json")
      BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toFlatJson(errors)))
    },
    rateInfo => { 
      val politician =  PoliticianDao.getById(rateInfo.politicianId).head
      RateDao.addRate(politician, rateInfo.rating, rateInfo.timestamp)
      Logger.debug(s"json: $rateInfo")
      val lastScore = RateDao.getScore(politician)
      Logger.debug(s"Last score for $politician : $lastScore")
      Ok(Json.toJson(lastScore))  
    }
  )
    
  }
  
  def rate(politicianId: Long, rate: Int) = Action { implicit request =>
    val politician = PoliticianDao.getById(politicianId).head
    RateDao.addRate(politician, rate, System.currentTimeMillis());
    Ok("rated")
   }

}
