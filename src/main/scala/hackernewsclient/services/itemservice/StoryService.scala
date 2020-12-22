package hackernewsclient.services.itemservice

import hackernewsclient.data.Client.Client
import hackernewsclient.data.item.Story
import hackernewsclient.data.Json

import scala.util.{Failure, Success, Try}

class StoryService(client: Client) extends ItemService {

  def isDeleted(response: ujson.Value): Boolean = {
    val deleted = Try(Json().readBoolean(response("deleted").toString()))
    deleted match {
      case Success(_) => true
      case Failure(_) => false
    }
  }

  def isStory(response: ujson.Value): Boolean = {
    val _type: String = Json().readString(response("type").toString())
    _type == "story"
  }

  def validateResponse(response: String): Boolean = {
    val pResponse = Json().parsed(response)
    !isDeleted(pResponse) && isStory(pResponse)
  }

  def getResponse(itemID: Long): String = client.getItem(itemID)

  def getItem(response: String): Story = Json().readStory(response)

  def getListOfItems(list: List[Long]): List[Story] = list.map(id => getItem(getResponse(id)))

  private def getStoriesFromAPI(storyType: String): List[Long] = {
    val response: String = client.getStories(storyType)
    Json().readItems(response)
  }

  def getTopStories: List[Long] = getStoriesFromAPI("topstories")

  def getNewStories: List[Long] = getStoriesFromAPI("newstories")

  def getBestStories: List[Long] = getStoriesFromAPI("beststories")

}
