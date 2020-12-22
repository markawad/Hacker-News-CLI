package hackernewsclient.service

import hackernewsclient.ResponseMocks
import hackernewsclient.data.Client.{APIClient, ClientProxy}
import org.scalatest.funsuite.AnyFunSuite
import hackernewsclient.data.User
import hackernewsclient.services.UserService


class UserServiceTest extends AnyFunSuite {

  val userSer: UserService = new UserService(ClientProxy(APIClient()))
  val responses: ResponseMocks = new ResponseMocks

  test("Valid userID response is validated") {
    val response: String = responses.user

    assert(userSer.validateResponse(response))
  }

  test("Invalid userID response throws IOException") {
    val response: String = "null"

    assert(!userSer.validateResponse(response))
  }

  test("Able to get user's submitted items") {
    val user: User = User(id = "abcd", about = "test", created = 23223432, karma = 1234, submitted = List[Long](1, 2, 3))

    val items: List[Long] = userSer.getUserItems(user)
    assert(items == List[Long](1, 2, 3))
  }

}
