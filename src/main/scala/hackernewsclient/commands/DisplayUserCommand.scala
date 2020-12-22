package hackernewsclient.commands

import hackernewsclient.Formatter
import hackernewsclient.data.User
import hackernewsclient.services.UserService

case class DisplayUserCommand(userSer: UserService, userID: String) extends Command {

  def execute(): Unit = {

    val response: String = userSer.getResponse(userID)

    val user: User = userSer.getUser(response)

    print(Formatter().formatUser(user))
  }
}
