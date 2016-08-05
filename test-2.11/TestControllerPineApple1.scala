/**
  * Created by hsu-admin on 2016/7/30.
  */

// For Unit Testing Controllers
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future
// frontend post data to backend
// vote 1
//  {
//    "vote" : 1,
//    "cake1Sum" : 5,
//    "cake2Sum" : 5 ,
//    "totalTickets": 11
//    "leftTickets" : 1,
//    "winner": 0
//  }
class TestControllerPineApple1 extends PlaySpec with Results{
  "PineApple1 #json" should {
    val PineApple = new controllers.PineApple()
    val result = PineApple.pineApple1().
      apply(FakeRequest(POST, "/").withJsonBody(Json.parse(
        """{ "vote" : 1,"cake1Sum" : 5,"cake2Sum" : 5 ,"totalTickets": 11,"leftTickets" : 1,"winner": 0 }""")))
    val jsonBody = contentAsJson(result)
      "should be return vote equal 0" in {
      //return zero vote
      val vote = (jsonBody \ "vote").as[Int]
      vote mustBe 0
      }
     "cake1Sum should plus 1 and return " in {
      val cake1Sum = (jsonBody \ "cake1Sum").as[Int]
      cake1Sum mustBe 6
      }
      "cake2Sum should not change " in {
      val cake2Sum = (jsonBody  \ "cake2Sum").as[Int]
      cake2Sum mustBe 5
      }

      "totalTickets should not change " in {
      val totalTickets = (jsonBody  \ "totalTickets").as[Int]
      totalTickets mustBe 11
      }

      "leftTickets should minus 1 " in {
      val leftTickets = (jsonBody  \ "leftTickets").as[Int]
      leftTickets mustBe 0
      }
      "winner should not change" in {
      val winner = (jsonBody  \ "winner").as[Int]
      winner mustBe 0
      }
    }

  "PineApple1 #json invalid value"should {
    val PineApple = new controllers.PineApple()
    val result = PineApple.pineApple1().
      apply(FakeRequest(POST, "/").withJsonBody(Json.parse(
        """{ "vote" : 1,"cake1Sum" : 5,"cake2Sum" : 5 ,"totalTickets": 11,"leftTickets" : 0,"winner": 0 }""")))
    val jsonBody = contentAsString(result)
    "should be leftTickets equal 0 shoud not do anything" in {
      jsonBody mustBe "not valid"

    }
  }


}


