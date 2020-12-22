package hackernewsclient.services.itemservice

import hackernewsclient.data.Client.Client
import hackernewsclient.data.item.Comment

class CommentService(client: Client) extends ItemService {

  def getResponse(itemID: Long): String = client.getItem(itemID)

  def getItem(response: String): Comment = json.readComment(response)

  def getListOfItems(list: List[Long]): List[Comment] = list.map(id => getItem(getResponse(id)))

}
