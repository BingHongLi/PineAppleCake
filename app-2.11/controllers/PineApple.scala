package controllers

/**
  * Created by alison on 2016/8/5.
  */

import play.api.mvc.{Action, BodyParsers, Controller,AnyContent}
import play.api._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


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



case class PineAppleJsonFormat(vote:Int,cake1Sum:Int,cake2Sum:Int,totalTickets:Int,leftTickets:Int,winner:Int)
class PineApple extends Controller with AccessLogging {

  val logger = Logger(this.getClass)
//  https://www.playframework.com/documentation/2.5.x/ScalaJsonAutomated
//  implicit val pineAppleRead2 = Json.reads[PineAppleJsonFormat]
//  implicit val pineAppleWrites2 = Json.writes[PineAppleJsonFormat]
  implicit val pineAppleFormat2 = Json.format[PineAppleJsonFormat]

//  implicit val pineAppleJsonWrites:Writes[PineAppleJsonFormat] =(
//    (JsPath \ "vote").write[Int]and
//      (JsPath \ "cake1Sum").write[Int] and
//      (JsPath \ "cake2Sum").write[Int] and
//      (JsPath \ "totalTickets").write[Int] and
//      (JsPath \ "leftTickets").write[Int] and
//      (JsPath \ "winner").write[Int]
//    )(unlift(PineAppleJsonFormat.unapply))
//
//  implicit val pineAppleJsonReads:Reads[PineAppleJsonFormat]=(
//    (JsPath \ "vote").read[Int](min(0) keepAnd max(2)) and
//      (JsPath \ "cake1Sum").read[Int] and
//      (JsPath \ "cake2Sum").read[Int] and
//      (JsPath \ "totalTickets").read[Int] and
//      (JsPath \ "leftTickets").read[Int] and
//      (JsPath \ "winner").read[Int](min(0) keepAnd max(3))
//    )(PineAppleJsonFormat.apply _ )
//  implicit val pineAppleFormat:Format[PineAppleJsonFormat] = Format(pineAppleJsonReads, pineAppleJsonWrites)

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



  def pineApple2() = Action(BodyParsers.parse.json){ request =>{
    logger.info("get request, we will parse the request body to an object")

    request.body.validate[PineAppleJsonFormat] match {
      case s :JsSuccess[PineAppleJsonFormat] =>{
        val bodyJson = s.get
        logger.info("caculate cake2Sum value")
        if (bodyJson.leftTickets > 0){
          val returnCake2Sum = bodyJson.vote match {
            case 0 =>   bodyJson.cake2Sum
            case 1 =>   bodyJson.cake2Sum
            case 2 => bodyJson.cake2Sum + 1
          }
          val returnLeftTickets = bodyJson.leftTickets - 1
          val pineAppleJson = PineAppleJsonFormat(
            0,
            bodyJson.cake1Sum,
            returnCake2Sum,
            bodyJson.totalTickets,
            returnLeftTickets,
            bodyJson.winner
          )
          logger.info("sent the resonpse with json")
          Ok(Json.toJson(pineAppleJson))

        }else{
          logger.warn("invalid value input")
          BadRequest("not valid")

        }

      }

    }
  }
  }


//  https://www.playframework.com/documentation/2.5.x/ScalaBodyParsers
  def pineAppleSum() = Action(BodyParsers.parse.json){  request =>{
//      val bodyJsonTest = request.body
//      val bodyJson = bodyJsonTest.asJson.get.validate[PineAppleJsonFormat].get
      logger.info("get request, we will parse the request body to an object")

      request.body.validate[PineAppleJsonFormat] match {

         case s :JsSuccess[PineAppleJsonFormat] => {
           val bodyJson = s.get
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
         case e:JsError => {
           logger.warn("invalid body input")
           BadRequest("Expecting application/json request body")
         }
      }


    }
  }

}
