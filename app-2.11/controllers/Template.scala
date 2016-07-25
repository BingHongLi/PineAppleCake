package controllers

/**
  * Created by BingHongLi on 2016/7/13.
  */

import play.api.mvc.Action
import play.api.mvc.Controller
import play.api._


class Template extends Controller with AccessLogging {

  val logger = Logger(this.getClass)

//  author: BingHongLi
  def test = Action{
    logger.info("just Test slf4j")

    Ok("JustTest")
  }



}
