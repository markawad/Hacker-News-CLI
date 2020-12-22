package hackernewsclient.services.itemservice

import hackernewsclient.data.Json
import hackernewsclient.data.item.Item

import scala.util.Try

abstract class ItemService {

  val json: Json = new Json

  def getResponse(itemID: Long): String

  def getItem(response: String): Item

  def getListOfItems(list: List[Long]): List[Item]

  def getItemKids(response: String): List[Long] = {
    val jsonObj = json.parsed(response)
    val kids: String = Try(jsonObj("kids").toString()).getOrElse("[]")
    json.readItems(kids)
  }

  def getListOfItemsToBeDisplayed(list: List[Long], pageNumber: Int, pageSize: Int): List[Long] = {
    val startPos = pageSize * (pageNumber - 1)
    list.slice(startPos, startPos + pageSize)
  }

}
