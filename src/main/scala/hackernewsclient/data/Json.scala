package hackernewsclient.data

import hackernewsclient.data.item.{Comment, Story}
import upickle.default._
import java.time.LocalDateTime

case class Json() {

  def readString(str: String): String = read[String](str)

  def readInt(str: String): Int = read[Int](str)

  def readBoolean(str: String): Boolean = read[Boolean](str)

  def readStory(response: String): Story = {
    implicit val itemRW: ReadWriter[Story] = macroRW
    read[Story](response)
  }

  def readComment(response: String): Comment = {
    implicit val itemRW: ReadWriter[Comment] = macroRW
    read[Comment](response)
  }

  def readUser(response: String): User = {
    implicit val itemRW: ReadWriter[User] = macroRW
    read[User](response)
  }

  def readItems(response: String): List[Long] = read[List[Long]](response)

  def parsed(str: String): ujson.Value = read[ujson.Value](str)

  def getID(response: String): Long = read[Long](parsed(response)("id"))

  def getuserID(response: String): String = read[String](parsed(response)("id"))

  def addCacheTtl(response: String, ttl: Int): String = {
    val parsedRes: ujson.Value = parsed(response)

    parsedRes("createdOn") = LocalDateTime.now().toString
    parsedRes("ttl") = ttl


    parsedRes.toString()
  }

  def getCacheTtl(response: String): Int = {
    val parsedRes: ujson.Value = parsed(response)

    readInt(parsedRes("ttl").toString())
  }

  def getCacheAddedDateTime(response: String): LocalDateTime = {
    val parsedRes: ujson.Value = parsed(response)
    val createdOn: String = read[String](parsedRes("createdOn"))

    LocalDateTime.parse(createdOn)
  }

}
