/**
  * Created by BingHongLi on 2016/7/14.
  */

// PlaySpec
import org.scalatestplus.play.PlaySpec

// For Unit Testing Controllers
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import scala.concurrent.Future

// For Mockito object
import org.scalatest.mock.MockitoSugar

trait IntDataService{
  def findData: List[Int]
}

class TestControllerTemplate extends PlaySpec with Results {

//  單對Controller 寫測試
  "Template #json" should {
    "should be valid" in {
      val templateController = new controllers.Template()
      val result : Future[Result] = templateController.test().apply(FakeRequest())
      val resultBody = contentAsString(result)
      resultBody mustBe "JustTest"
    }
  }


}
