package hackernewsclient.service

import hackernewsclient.ResponseMocks
import hackernewsclient.data.Client.{APIClient, ClientProxy}
import hackernewsclient.data.Json
import org.scalatest.funsuite.AnyFunSuite
import hackernewsclient.services.itemservice.StoryService

class StoryServiceTest extends AnyFunSuite {

  val storySer: StoryService = new StoryService(ClientProxy(APIClient()))
  val json: Json = Json()
  val responses: ResponseMocks = new ResponseMocks

  test("Story page contains correct indicies") {
    val pageNumber = 2
    val pageSize = 3
    val list: List[Long] = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    val itemIDs = storySer.getListOfItemsToBeDisplayed(list = list, pageNumber = pageNumber, pageSize = pageSize)
    assert(itemIDs == List(3, 4, 5))
  }

  test("Page size contains more space than available stories is okay") {
    val pageNumber = 3
    val pageSize = 4
    val list: List[Long] = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    val itemIDs = storySer.getListOfItemsToBeDisplayed(list = list, pageNumber = pageNumber, pageSize = pageSize)
    assert(itemIDs == List(8, 9))
  }

  test("Page number and size exceed the given list and should return empty") {
    val pageNumber = 100
    val pageSize = 20
    val list: List[Long] = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    val itemIDs = storySer.getListOfItemsToBeDisplayed(list = list, pageNumber = pageNumber, pageSize = pageSize)
    assert(itemIDs == List())
  }

  test("Negative page number and size should just return empty") {
    val pageNumber = -3
    val pageSize = -4
    val list: List[Long] = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    val itemIDs = storySer.getListOfItemsToBeDisplayed(list = list, pageNumber = pageNumber, pageSize = pageSize)
    assert(itemIDs == List())
  }

  test("Return true if response is of type story") {
    val response: ujson.Value = json.parsed(responses.story1)

    assert(storySer.isStory(response))
  }

  test("Return false if response is not of type story") {
    val response: ujson.Value = json.parsed(responses.comment1)

    assert(!storySer.isStory(response))
  }

  test("Find a valid (non deleted) story from the response") {
    val response: ujson.Value = json.parsed(responses.story1)

    assert(!storySer.isDeleted(response))
  }

  test("Find a deleted story from the response") {
    val response: ujson.Value = json.parsed(responses.deletedStory)

    assert(storySer.isDeleted(response))
  }

}
