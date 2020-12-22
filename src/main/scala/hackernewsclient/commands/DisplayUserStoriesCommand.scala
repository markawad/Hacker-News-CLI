package hackernewsclient.commands

import hackernewsclient.Formatter
import hackernewsclient.data.User
import hackernewsclient.data.item.Story
import hackernewsclient.services.{UserService, UserStoriesService}

import scala.collection.View

case class DisplayUserStoriesCommand(userSer: UserService,
                                     userStoriesSer: UserStoriesService,
                                     userID: String, pageNumber: Int, pageSize: Int) extends Command {

  def execute(): Unit = {

    val response: String = userSer.getResponse(userID)

    val user: User = userSer.getUser(response)

    def itemIDs: List[Long] = userSer.getUserItems(user)

    def items: View[Story] = userStoriesSer.getUserStories(itemIDs, pageNumber, pageSize)

    items.map(Formatter().formatStory).foreach(print)
  }
}
