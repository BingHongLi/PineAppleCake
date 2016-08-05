/**
  * Created by hsu-admin on 2016/7/30.
  */


import org.scalatestplus.play._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._


class TestActionPineApple1 extends PlaySpec with OneAppPerSuite with Controller{
  implicit lazy val materializer : akka.stream.Materializer  = app.materializer
  "An essential action" should {
    val action: EssentialAction = Action { request =>
      val body: AnyContent = request.body
      val jsonBody: Option[JsValue] = body.asJson
      // Expecting json body
      jsonBody.map { json =>
        val vote = (json \ "vote").get.as[Int]
        val cake1Sum = (json \ "cake1Sum").get.as[Int]
        val cake2Sum = (json \ "cake2Sum").get.as[Int]
        val totalTickets = (json \ "totalTickets").get.as[Int]
        val leftTickets = (json \ "leftTickets").get.as[Int]
        val winner = (json \ "winner").get.as[Int]
        if (leftTickets <= 0){

          BadRequest("not valid")
        } else{
          val returnVote = 0
          val returnCake1Sum = vote match{
            case 1 => cake1Sum + 1
            case _ => cake1Sum
          }
          val returnLeftTickets = leftTickets - 1

          val returnJson = Json.obj(
            "vote" -> returnVote, "cake1Sum" -> returnCake1Sum,
            "cake2Sum" -> cake2Sum, "totalTickets" -> totalTickets,
            "leftTickets" -> returnLeftTickets, "winner" -> winner
          )
          Ok(returnJson)
        }
      }.getOrElse {

        BadRequest("Expecting application/json request body")
      }
    }
    "can transfer ok " in {
      //    需混入Controller，但官網卻沒有混入，hans or alison 這邊可測一下
      val request = FakeRequest(POST, "/").withJsonBody(Json.parse(
        """{ "vote" : 1,"cake1Sum" : 5,"cake2Sum" : 5 ,"totalTickets": 11,"leftTickets" : 1,"winner": 0 }"""))
      val result  = call(action, request)
      status(result) mustEqual OK
      contentAsString(result) mustEqual """{"vote":0,"cake1Sum":6,"cake2Sum":5,"totalTickets":11,"leftTickets":0,"winner":0}"""
    }
  }
}
