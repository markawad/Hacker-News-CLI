package hackernewsclient.data.item

abstract class Item {

  def id: Long

  def by: String

  def kids: List[Long]

}
