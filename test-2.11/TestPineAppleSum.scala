/**
  * Created by BingHongLi on 2016/7/25.
  */
import org.scalatestplus.play._
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._

class TestPineAppleSum  extends PlaySpec with OneAppPerSuite with Controller {

  implicit lazy val materializer : akka.stream.Materializer  = app.materializer

  import play.api.libs.json._
  "PineAppleSum Action" should {
    "pineapple1 must be winner"  in {

      val request = FakeRequest("POST","/pineAppleSum").withJsonBody((Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 6,
                         "cake2Sum" : 3,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 0
                         }"""))).withHeaders((CONTENT_TYPE,"text/json"))

      val result = call(new controllers.PineApple().pineAppleSum, request)

      contentAsJson(result) mustEqual (Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 6,
                         "cake2Sum" : 3,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 1
                         }"""))
    }
    "pineApple2 must be winner" in {
      val request = FakeRequest("POST","/pineAppleSum").withJsonBody((Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 3,
                         "cake2Sum" : 6,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 0
                         }"""))).withHeaders((CONTENT_TYPE,"text/json"))

      val result = call(new controllers.PineApple().pineAppleSum, request)

      contentAsJson(result) mustEqual (Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 3,
                         "cake2Sum" : 6,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 2
                         }"""))
    }
    "must be draw" in {
      val request = FakeRequest("POST","/pineAppleSum").withJsonBody((Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 5,
                         "cake2Sum" : 5,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 0
                         }"""))).withHeaders((CONTENT_TYPE,"text/json"))

      val result = call(new controllers.PineApple().pineAppleSum, request)

      contentAsJson(result) mustEqual (Json.parse("""{
                         "vote" : 0,
                         "cake1Sum" : 5,
                         "cake2Sum" : 5,
                         "totalTickets": 11,
                         "leftTickets" : 2,
                         "winner": 3
                         }"""))
    }

//    "the request body is invalid" in {
//      val request = FakeRequest("POST","/pineAppleSum")
//      request.withBody("""{
//                         "vote" : 0,
//                         "cake 1 sum" : 5,
//                         "cake 2 sum" : 5 ,
//                         "left tickets" : 1,
//                         }""")
//      val result = call(new controllers.PineApple().pineAppleSum, request)
//      contentAsString(result) mustEqual ("""{ }""")
//
//    }

  }

}
