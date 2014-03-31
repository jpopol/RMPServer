package controllers.dao

/**
 * Created with IntelliJ IDEA.
 * User: ejeabon
 * Date: 3/25/14
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */

import play.api.Play.current
import scala.slick.lifted.TableQuery
import models.{Rate, Rates,  Politician}
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._

object RateDao {

  val rateQuery : TableQuery[Rates] = TableQuery[Rates]

  def getAll(p:Politician):List[Rate] = {
    DB withSession { implicit session =>
      rateQuery.filter(_.polID === p.id).list
    }
  }

  def addRate(p:Politician, rate:Int) = {
    DB.withSession{ implicit session =>
      rateQuery += Rate(None, System.currentTimeMillis(), rate, p.id)
    }
  }
}
