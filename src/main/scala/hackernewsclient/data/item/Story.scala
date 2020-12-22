package hackernewsclient.data.item

case class Story(id: Long,
                 by: String,
                 kids: List[Long] = List[Long](),
                 title: String = "",
                 url: String = "",
                 descendants: Int = 0,
                 score: Int = 0) extends Item {}
