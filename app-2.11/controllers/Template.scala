package controllers

/**
  * Created by BingHongLi on 2016/7/13.
  */

import play.api.mvc.Action
import play.api.mvc.Controller

class Template extends Controller{

  def test = Action{

    Ok("JustTest")
  }



}
