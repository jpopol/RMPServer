package controllers

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.ws.WS

class PoliticiansSpec extends Specification {


  "Politicians" should {
    "list should return politician" in {
      running(TestServer(3333)) {
        val result = controllers.Politicians.list()(FakeRequest())

        status(result) must equalTo(OK)
        contentType(result) must beSome("text/html")
        charset(result) must beSome("utf-8")
        contentAsString(result) must contain("Independent") and contain("Sinn Fein") and contain("Labour")

      }
    }

    "Independent should only return independent politicians" in {
      running(TestServer(3333)) {
        val result = controllers.Politicians.showParty("Independent")(FakeRequest())

        status(result) must equalTo(OK)
        contentType(result) must beSome("text/html")
        contentAsString(result) must contain("Independent")
        contentAsString(result) must not contain("Sinn Fein") and not contain("Labour") and contain("Independent")

      }
    }
  }

}
