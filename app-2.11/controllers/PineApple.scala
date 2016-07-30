package controllers

/**
  * Created by BingHongLi on 2016/7/25.
  */

import play.api.mvc.{Action, BodyParsers, Controller}
import play.api._

//JSON Example

//request
//{
//"vote" : 0,
//"cake1Sum" : 6,
//"cake2Sum" : 3 ,
//"totalTickets": 11
//"leftTickets" : 2,
//"winner": 0
//}
//
//response
//{
//"vote" : 0,
//"cake1sum" : 6,
//"cake2Sum" : 3 ,
//"totalTickets": 11
//"leftTickets" : 2,
//"winner": 1
//}

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

case class PineAppleJsonFormat(vote:Int,cake1Sum:Int,cake2Sum:Int,totalTickets:Int,leftTickets:Int,winner:Int)
class PineApple extends Controller with AccessLogging {

  val logger = Logger(this.getClass)

  implicit val pineAppleJsonWrites:Writes[PineAppleJsonFormat] =(
    (JsPath \ "vote").write[Int]and
      (JsPath \ "cake1Sum").write[Int] and
      (JsPath \ "cake2Sum").write[Int] and
      (JsPath \ "totalTickets").write[Int] and
      (JsPath \ "leftTickets").write[Int] and
      (JsPath \ "winner").write[Int]
    )(unlift(PineAppleJsonFormat.unapply))

  implicit val pineAppleJsonReads:Reads[PineAppleJsonFormat]=(
    (JsPath \ "vote").read[Int](min(0) keepAnd max(2)) and
      (JsPath \ "cake1Sum").read[Int] and
      (JsPath \ "cake2Sum").read[Int] and
      (JsPath \ "totalTickets").read[Int] and
      (JsPath \ "leftTickets").read[Int] and
      (JsPath \ "winner").read[Int](min(0) keepAnd max(3))
    )(PineAppleJsonFormat.apply _ )
  implicit val pineAppleFormat:Format[PineAppleJsonFormat] = Format(pineAppleJsonReads, pineAppleJsonWrites)


  def pineApple1() = TODO

  def pineApple2() = TODO

//  https://www.playframework.com/documentation/2.5.x/ScalaBodyParsers
  def pineAppleSum() = Action(BodyParsers.parse.json){  request =>{
//      val bodyJsonTest = request.body
//      val bodyJson = bodyJsonTest.asJson.get.validate[PineAppleJsonFormat].get
      logger.info("get request, we will parse the request body to an object")
      val bodyJson = request.body.validate[PineAppleJsonFormat] match {
         case s :JsSuccess[PineAppleJsonFormat] => {
           s.get
         }
      }

      logger.info("caculate winner's value")
      val winnerState = if(bodyJson.cake1Sum > bodyJson.cake2Sum){
        1
      }else if (bodyJson.cake1Sum < bodyJson.cake2Sum) {
        2
      }else{
        3
      }

      logger.info("transform to a json result")
      val pineAppleJson = PineAppleJsonFormat(
        0,
        bodyJson.cake1Sum,
        bodyJson.cake2Sum,
        bodyJson.totalTickets,
        bodyJson.leftTickets,
        winnerState
      )

      logger.info("sent the resonpse with json")
      Ok(Json.toJson(pineAppleJson))
    }
  }

}
