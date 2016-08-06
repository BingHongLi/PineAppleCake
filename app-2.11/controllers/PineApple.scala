package controllers

/**
  * Created by alison on 2016/8/5.
  */

import play.api.mvc.{Action, BodyParsers, Controller}
import play.api._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class PineAppleJsonFormat(vote:Int,cake1Sum:Int,cake2Sum:Int,totalTickets:Int,leftTickets:Int,winner:Int)

class PineApple extends Controller with AccessLogging{
  val logger = Logger(this.getClass)

  implicit val pineAppleFormat2 = Json.format[PineAppleJsonFormat]

  def pineApple1() = TODO

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


  def pineAppleSum() = TODO

}
