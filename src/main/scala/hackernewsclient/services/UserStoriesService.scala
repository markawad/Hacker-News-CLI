package hackernewsclient.services

import hackernewsclient.data.Client.Client
import hackernewsclient.data.item.Story
import hackernewsclient.services.itemservice.StoryService

import scala.collection.View

class UserStoriesService(client: Client) extends StoryService(client) {

  def getBeginningOfList(fullList: List[Long], pageNumber: Int, pageSize: Int): List[Long] = {
    val startPos = pageSize * (pageNumber - 1)

    // drop first n items till reaching first position
    fullList.drop(startPos)
  }

  def getUserStories(fullList: List[Long], pageNumber: Int, pageSize: Int): View[Story] = {
    val list: List[Long] = getBeginningOfList(fullList, pageNumber, pageSize)

    list.view.map(getResponse).filter(validateResponse).map(getItem).take(pageSize)
  }
}
