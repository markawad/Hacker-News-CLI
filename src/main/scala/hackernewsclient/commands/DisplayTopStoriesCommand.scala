package hackernewsclient.commands

import hackernewsclient.Formatter
import hackernewsclient.data.item.Story
import hackernewsclient.services.itemservice.StoryService

case class DisplayTopStoriesCommand(storySer: StoryService, pageNumber: Int, pageSize: Int) extends Command {

  def execute(): Unit = {

    val responseIDs: List[Long] = storySer.getTopStories

    val itemIDs: List[Long] = storySer.getListOfItemsToBeDisplayed(list = responseIDs, pageNumber, pageSize)

    val items: List[Story] = storySer.getListOfItems(itemIDs)

    items.map(Formatter().formatStory).foreach(print)
  }

}
