package hackernewsclient.data.Client

trait Client {

  def getStories(storyType: String): String

  def getItem(itemID: Long): String

  def getUser(userID: String): String

  def getUpdates: String

}
