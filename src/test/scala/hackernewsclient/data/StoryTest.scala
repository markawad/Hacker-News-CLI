package hackernewsclient.data

import hackernewsclient.ResponseMocks
import org.scalatest.funsuite.AnyFunSuite
import hackernewsclient.data.item.Story

class StoryTest extends AnyFunSuite {

  val responses: ResponseMocks = new ResponseMocks
  val json: Json = Json()

  test("Response with no kids parameter does not raise an error") {
    val story: Story = json.readStory(responses.storyWithEmptyParamKids)

    assert(story.kids == List[Long]())
  }

  test("Response with no title parameter does not raise an error") {
    val story: Story = json.readStory(responses.storyWithEmptyParamTitle)

    assert(story.title == "")
  }

  test("Response with no url parameter does not raise an error") {
    val story: Story = json.readStory(responses.storyWithEmptyParamUrl)

    assert(story.url == "")
  }

  test("Response with no descendants parameter does not raise an error") {
    val story: Story = json.readStory(responses.storyWithEmptyParamDescendants)

    assert(story.descendants == 0)
  }

  test("Response with no score parameter does not raise an error") {
    val story: Story = json.readStory(responses.storyWithEmptyParamScore)

    assert(story.score == 0)
  }



}
