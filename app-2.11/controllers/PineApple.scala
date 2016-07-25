package controllers

/**
  * Created by BingHongLi on 2016/7/25.
  */

import play.api.mvc.Action
import play.api.mvc.Controller
import play.api._

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

  def pineApple1() = TODO

  def pineApple2() = TODO

  def pineAppleSum() = TODO

}
