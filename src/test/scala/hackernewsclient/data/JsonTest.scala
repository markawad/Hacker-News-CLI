package hackernewsclient.data

import hackernewsclient.ResponseMocks
import org.scalatest.funsuite.AnyFunSuite
import hackernewsclient.data.item.{Comment, Story}

class JsonTest extends AnyFunSuite {

  val responses: ResponseMocks = new ResponseMocks
  val json: Json = Json()

  test("Read boolean") {
    val jsonObj = json.parsed(responses.deletedStory)

    assert(json.readBoolean(jsonObj("deleted").toString()))
  }

  test("Read Int") {
    val jsonObj = json.parsed(responses.intJson)

    assert(json.readInt(jsonObj("int").toString()).isInstanceOf[Int])
  }

  test("Read Story") {
    assert(json.readStory(responses.story1).isInstanceOf[Story])
  }

  test("Read Comment") {
    assert(json.readComment(responses.comment1).isInstanceOf[Comment])
  }

  test("Read User") {
    assert(json.readUser(responses.user).isInstanceOf[User])
  }

  test("Read Items") {
    assert(json.readItems(responses.topstories).isInstanceOf[List[Long]])
  }

  test("Get id") {
    val story: Story = json.readStory(responses.story1)

    assert(json.getID(responses.story1) == story.id)
  }

  test("Add cache ttl") {
    val response = json.addCacheTtl(responses.story1, ttl = 600)

    assert(response.contains("\"ttl\":600") )
  }



}
