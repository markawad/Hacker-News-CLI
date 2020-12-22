package hackernewsclient.commands

import hackernewsclient.Formatter
import hackernewsclient.data.Client.APIClient
import hackernewsclient.data.item.Comment
import hackernewsclient.services.itemservice.CommentService

case class DisplayCommentsCommand(commentSer: CommentService, itemID: Long, pageNumber: Int, pageSize: Int) extends Command {

  def execute(): Unit = {

    val response: String = APIClient().getItem(itemID)

    val itemIDs: List[Long] = {
      val list: List[Long] = commentSer.getItemKids(response)

      commentSer.getListOfItemsToBeDisplayed(list, pageNumber, pageSize)
    }

    val items: List[Comment] = commentSer.getListOfItems(itemIDs)

    items.map(Formatter().formatComment).foreach(print)
  }
}
