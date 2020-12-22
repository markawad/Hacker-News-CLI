package hackernewsclient

import hackernewsclient.commands._
import hackernewsclient.data.Cache
import hackernewsclient.data.Client.{APIClient, Client, ClientProxy}
import hackernewsclient.services.{UserService, UserStoriesService}
import hackernewsclient.services.itemservice.{CommentService, StoryService}

import scala.sys.exit

object Main {

  def usage: String = {
    "Usage: hackernewsclient [options] [command] [command-options]\n" +
      "Help: hackernewsclient --help\n"
  }

  def main(args: Array[String]): Unit = {

    var argsList = args.toList

    if(argsList.isEmpty) {
      print(usage)
      exit(1)
    }

    def getTtl: Int = {
      if (argsList.head.contains("--ttl=")) {
        val ttl = argsList.head.split('=')(1).toInt
        argsList = argsList.drop(1)
        ttl
      } else 600
    }

    // dafaults
    var pageNumber: Int = 1
    var pageSize: Int = 5
    val ttl = getTtl

    def getAPIProxy(ttl: Int): Client = ClientProxy(APIClient(), ttl)

    def parsePageArgs(args: List[String]): Unit = {
      args.foreach {
        case page if page contains "--page=" => pageNumber = page.split('=')(1).toInt
        case size if size contains "--size=" => pageSize = size.split('=')(1).toInt
        case _ => {
          print(usage)
          exit(1)
        }
      }
    }

    argsList match {
      case "top-stories" :: _ if argsList.length <= 3 => {
        parsePageArgs(argsList.drop(1))
        val storySer: StoryService = new StoryService(getAPIProxy(ttl))
        DisplayTopStoriesCommand(storySer, pageNumber, pageSize).execute()
      }
      case "best-stories" :: _ if argsList.length <= 3 => {
        parsePageArgs(argsList.drop(1))
        val storySer: StoryService = new StoryService(getAPIProxy(ttl))
        DisplayBestStoriesCommand(storySer, pageNumber, pageSize).execute()
      }
      case "new-stories" :: _ if argsList.length <= 3 => {
        parsePageArgs(argsList.drop(1))
        val storySer: StoryService = new StoryService(getAPIProxy(ttl))
        DisplayNewStoriesCommand(storySer, pageNumber, pageSize).execute()
      }
      case user :: _ if (argsList.length == 1) && (user contains "--user=") => {
        val userID: String = user.split('=')(1)

        val userSer: UserService = new UserService(getAPIProxy(ttl))
        DisplayUserCommand(userSer, userID).execute()
      }
      case user :: "--display-stories" :: _ if (argsList.length <= 4) && (user contains "--user=") => {
        parsePageArgs(argsList.drop(2))
        val userID: String = user.split('=')(1)

        val userSer: UserService = new UserService(getAPIProxy(ttl))
        val userStoriesSer: UserStoriesService = new UserStoriesService(getAPIProxy(ttl))
        DisplayUserStoriesCommand(userSer, userStoriesSer, userID, pageNumber, pageSize).execute()
      }
      case displayComments :: _ if (argsList.length <= 3) && (displayComments contains "--display-comments=") => {
        parsePageArgs(argsList.drop(1))
        val itemID: Long = displayComments.split('=')(1).toInt

        val commentSer: CommentService = new CommentService(getAPIProxy(ttl))
        DisplayCommentsCommand(commentSer, itemID, pageNumber, pageSize).execute()
      }
      case help :: Nil if help == "--help" || help == "-h" => {
        val source = scala.io.Source.fromResource("help.txt")
        val lines: String = try source.getLines().mkString("\n") finally source.close()
        print(lines)
      }
      case "clear-cache" :: Nil => {
        Cache().clear()
        println("Cache cleared")
      }
      case _ => {
        print(usage)
        exit(1)
      }
    }

  }

}
