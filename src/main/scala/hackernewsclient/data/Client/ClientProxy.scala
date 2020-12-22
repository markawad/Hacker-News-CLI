package hackernewsclient.data.Client

import hackernewsclient.data.Cache

case class ClientProxy(service: Client, ttl: Int = 600) extends Client {

  val location: String = "cache/"
  val cache: Cache = Cache(ttl)

  def itemUpdated(itemID: Long): Boolean = getUpdates.contains(itemID)

  def userUpdated(userID: String): Boolean = getUpdates.contains(userID)

  def getStories(storyType: String): String = service.getStories(storyType)

  def getItem(itemID: Long): String = {
    if (!itemUpdated(itemID) && cache.isValid(itemID.toString)) {
      cache.getCache(itemID.toString)
    } else {
      val response: String = service.getItem(itemID)
      cache.addItem(response)
      response
    }
  }

  def getUser(userID: String): String = {
    if(!userUpdated(userID) && cache.isValid(userID))
      cache.getCache(userID)
    else {
      val response: String = service.getUser(userID)
      cache.addUser(response)
      response
    }
  }

  def getUpdates: String = service.getUpdates

}
