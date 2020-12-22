package hackernewsclient.data.Client

import scala.io.Source

case class APIClient() extends Client {
  private val base_url: String = s"https://hacker-news.firebaseio.com/v0/"

  private def getResponseFromUrl(url: String): String = {
    val s = Source.fromURL(url)
    s.getLines().mkString("\n")
  }

  def getStories(storyType: String): String = {
    val url = base_url + storyType + ".json"
    getResponseFromUrl(url)
  }

  def getItem(itemID: Long): String = {
    val url = base_url + "item/" + itemID + ".json"
    getResponseFromUrl(url)
  }

  def getUser(userID: String): String = {
    val url = base_url + "user/" + userID + ".json"
    getResponseFromUrl(url)
  }

  def getUpdates: String = {
    val url = base_url + "updates.json"
    getResponseFromUrl(url)
  }

}
