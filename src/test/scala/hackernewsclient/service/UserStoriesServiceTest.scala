package hackernewsclient.service

import hackernewsclient.data.Client.{APIClient, ClientProxy}
import org.scalatest.funsuite.AnyFunSuite
import hackernewsclient.services.UserStoriesService

class UserStoriesServiceTest extends AnyFunSuite {
  val userStoriesSer: UserStoriesService = new UserStoriesService(ClientProxy(APIClient()))

  test("Get first itemID in the list based on page number and size") {
    val pageNumber = 2
    val pageSize = 3
    val fullList: List[Long] = List[Long](0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    val list: List[Long] = userStoriesSer.getBeginningOfList(fullList, pageNumber, pageSize)
    assert(list == List[Long](3, 4, 5, 6, 7, 8, 9))
  }

}
