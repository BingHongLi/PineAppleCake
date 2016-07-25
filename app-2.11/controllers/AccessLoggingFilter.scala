package controllers

/**
  * Created by BingHongLi on 2016/7/25.
  */
import javax.inject.Inject
import akka.stream.Materializer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.Logger
import play.api.mvc._

//導入不同訊息到不同檔案
class AccessLoggingFilter @Inject() (implicit val mat: Materializer) extends Filter {

  val accessLogger = Logger("access")

  def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    val resultFuture = next(request)

    resultFuture.foreach(result => {
      val msg = s"[${request.method}] [remote-address=${request.remoteAddress}] [uri=${request.uri}] " +
        s" [status=${result.header.status}]";
      accessLogger.info(msg)
    })

    resultFuture
  }
}
