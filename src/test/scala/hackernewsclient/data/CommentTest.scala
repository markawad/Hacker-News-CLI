package hackernewsclient.data

import hackernewsclient.ResponseMocks
import org.scalatest.funsuite.AnyFunSuite
import hackernewsclient.data.item.Comment

class CommentTest extends AnyFunSuite {

  val responses: ResponseMocks = new ResponseMocks
  val json: Json = Json()

  test("Response with no kids parameter does not raise an error") {
    val comment: Comment = json.readComment(responses.commentWithEmptyParamKids)

    assert(comment.kids == List[Long]())
  }

}
