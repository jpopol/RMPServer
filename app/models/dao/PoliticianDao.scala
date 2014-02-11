package models.dao

import scala.slick.lifted.TableQuery
import models.{Politician, Politicians}

import models.RmpDb._

// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._
// Use the implicit threadLocalSession
//import Database.threadLocalSession

object PoliticianDao {
  private val politicianQuery: TableQuery[Politicians] = TableQuery[Politicians]

  def list():Set[Politician] = {
    database withSession { implicit session =>
      ( for {
          p <- politicianQuery
      }yield(p)).run.toSet
      //politicianQuery.list().run
    }
  }


  def filterByConstituency(constituency: String):Set[Politician] = {
    database withSession { implicit session =>
      ( for {
        p <- politicianQuery if p.constituency === constituency
      }yield(p)).run.toSet
      //politicianQuery.filter(_.constituency === constituency).run
    }
  }


  def filterByParty(party: String):Set[Politician] = {
    database withSession { implicit session =>
      ( for {
        p<- politicianQuery if p.party === party
      }yield(p)).run.toSet
      //politicianQuery.filter(_.party === party).run
    }
  }

  def getById(id: Long):Set[Politician] = {
    database withSession { implicit session =>
      ( for {
        p <- politicianQuery if p.id === id
      }yield(p)).run.toSet
      //politicianQuery.filter(_.id === id).run
    }
  }

}
