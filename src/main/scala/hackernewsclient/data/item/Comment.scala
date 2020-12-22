package hackernewsclient.data.item

case class Comment(id: Long,
                   by: String,
                   kids: List[Long] = List[Long](),
                   text: String) extends Item {}
