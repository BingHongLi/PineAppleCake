package controllers

/**
  * Created by BingHongLi on 2016/7/25.
  */
import scala.concurrent.Future
import play.api.Logger
import play.api.mvc._
//用來定義自己log格式的trait
trait AccessLogging {

  val accessLogger = Logger("access")

  object AccessLoggingAction extends ActionBuilder[Request] {

    def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
      accessLogger.info(s"[${request.method}] [remote-address=${request.remoteAddress}] [uri=${request.uri}] ")
      block(request)
    }
  }
}
