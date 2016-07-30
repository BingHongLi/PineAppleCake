package controllers

/**
  * Created by BingHongLi on 2016/7/25.
  */

import play.api.mvc.{Action, AnyContent, Controller}
import play.api._
import play.api.libs.json._

//JSON Example

//request
//{
//"vote" : 0,
//"cake 1 sum" : 6,
//"cake 2 sum" : 3 ,
//"total tickets": 11
//"left tickets" : 2,
//"winner": 0
//}
//
//response
//{
//"vote" : 0,
//"cake 1 sum" : 6,
//"cake 2 sum" : 3 ,
//"total tickets": 11
//"left tickets" : 2,
//"winner": 1
//}

class PineApple extends Controller with AccessLogging {

  val logger = Logger(this.getClass)

  def pineApple1() = AccessLoggingAction{
    request =>
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
          logger.warn("invalid value input")
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
        logger.info("return ok")
        Ok(returnJson)
        }
      }.getOrElse {
        logger.warn("invalid body input")
        BadRequest("Expecting application/json request body")
      }
  }


  def pineApple2() = TODO

  def pineAppleSum() = TODO

}
