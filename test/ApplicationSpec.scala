package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class  ApplicationSpec extends Specification {
  
  "Application" should {

    "send 404 on a bad request" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the index page" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val home = route(FakeRequest(GET, "/")).get

        status(home) must equalTo(SEE_OTHER)
      }
    }
  }
}