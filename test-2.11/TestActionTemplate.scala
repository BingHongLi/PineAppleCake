/**
  * Created by BingHongLi on 2016/7/14.
  */


import org.scalatestplus.play._

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._



class TestActionTemplate extends PlaySpec with OneAppPerSuite with Controller {

//  app 來自於 Trait OneAppSuite
//  akka.stream.Materializer
  implicit lazy val materializer : akka.stream.Materializer  = app.materializer

  "An essential action" should {
    "can transfer ok " in {
//    需混入Controller，但官網卻沒有混入，hans or alison 這邊可測一下
      val action: EssentialAction = Action { request =>
        Ok("JustTest")
      }
      val request = FakeRequest("GET","/")
      val result  = call(action, request)
      status(result) mustEqual OK
      contentAsString(result) mustEqual("JustTest")
    }
  }

}
