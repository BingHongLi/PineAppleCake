/**
  * Created by alison on 2016/8/5.
  */

import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._

class TestActionPineApple2 extends PlaySpec with OneAppPerSuite with Controller {

  //  app 來自於 Trait OneAppSuite
  //  akka.stream.Materializer
  implicit lazy val materializer : akka.stream.Materializer  = app.materializer

  "PineApple2 Action" should {
    "cake2Sum should plus 1 and return" in {
      val request = FakeRequest("POST","/pineApple2").withJsonBody((Json.parse("""{
                         "vote" : 2,
                         "cake1Sum" : 6,
                         "cake2Sum" : 3,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 0
                         }"""))).withHeaders((CONTENT_TYPE,"text/json"))

      val result = call(new controllers.PineApple().pineApple2, request)

      contentAsJson(result) mustEqual (Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 6,
                         "cake2Sum" : 4,
                         "totalTickets": 11,
                         "leftTickets" : 1,
                         "winner": 0
                         }"""))
    }
    "be return vote equal 0 & cake2Sum,totalTickets,winner should not change" in {
      val request = FakeRequest("POST","/pineApple2").withJsonBody((Json.parse("""{
                         "vote" : 1,
                         "cake1Sum" : 6,
                         "cake2Sum" : 3,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 0
                         }"""))).withHeaders((CONTENT_TYPE,"text/json"))

      val result = call(new controllers.PineApple().pineApple2, request)

      contentAsJson(result) mustEqual (Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 6,
                         "cake2Sum" : 3,
                         "totalTickets": 11,
                         "leftTickets" : 1,
                         "winner": 0
                         }"""))
    }
    "leftTickets should not <= 0" in {
      val request = FakeRequest("POST","/pineApple2").withJsonBody((Json.parse("""{
                         "vote" : 1,
                         "cake1Sum" : 6,
                         "cake2Sum" : 3,
                         "totalTickets": 11,
                         "leftTickets" : 0,
                         "winner": 0
                         }"""))).withHeaders((CONTENT_TYPE,"text/json"))

      val result = call(new controllers.PineApple().pineApple2, request)

      status(result) mustEqual BadRequest.header.status
    }


  }


}
