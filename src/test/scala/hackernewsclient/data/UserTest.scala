package hackernewsclient.data

import hackernewsclient.ResponseMocks
import org.scalatest.funsuite.AnyFunSuite

class UserTest extends AnyFunSuite {

  val responses: ResponseMocks = new ResponseMocks
  val json: Json = Json()

  test("Response with empty about section does not raise an error") {
    val user: User = json.readUser(responses.userWithEmptyParamAbout)

    assert(user.about == "")
  }

  test("Response with empty submitted items does not raise an error") {
    val user: User = json.readUser(responses.userWithEmptyParamSubmitted)

    assert(user.submitted == List[Long]())
  }

}
