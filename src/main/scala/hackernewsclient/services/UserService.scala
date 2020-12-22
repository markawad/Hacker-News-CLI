package hackernewsclient.services

import hackernewsclient.data.Client.Client
import hackernewsclient.data.{Json, User}

class UserService(client: Client) {

  def getResponse(userID: String): String = client.getUser(userID)

  def validateResponse(response: String): Boolean = response != "null"

  def getUser(response: String): User = {
    validateResponse(response)

    Json().readUser(response)
  }

  def getUserItems(user: User): List[Long] = user.submitted

}
