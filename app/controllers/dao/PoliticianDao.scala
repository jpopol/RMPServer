package controllers.dao

import play.api.Play.current
import scala.slick.lifted.TableQuery
import models.{Politicians, Politician}
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._


object PoliticianDao {
  private val politicianQuery: TableQuery[Politicians] = TableQuery[Politicians]

  def list():List[Politician] = {
    DB withSession { implicit session =>
      politicianQuery.list
    }
  }

  def filterByConstituency(constituency: String):List[Politician] = {
    DB withSession { implicit session =>
      politicianQuery.filter(_.constituency === constituency).list
    }
  }

  def filterByParty(party: String):List[Politician] = {
    DB withSession { implicit session =>
     politicianQuery.filter(_.party === party).list
    }
  }

  def getById(id: Long):List[Politician] = {
    DB withSession { implicit session =>
     politicianQuery.filter(_.id === id).list
    }
  }

}
