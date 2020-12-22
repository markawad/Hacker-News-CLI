package hackernewsclient.data

import java.io.File
import java.nio.file.{Files, Paths}

import hackernewsclient.ResponseMocks
import org.scalatest.funsuite.AnyFunSuite

class CacheTest extends AnyFunSuite {

  val cache: Cache = Cache()
  val responses: ResponseMocks = new ResponseMocks
  val json: Json = Json()

  def deleteAddedFile(name: String): Unit = {
    val path: String = cache.location + name + ".json"
    val file: File = new File(path)
    file.delete()
  }

  test("Add item") {
    cache.addItem(responses.story1)
    val id: Long = json.getID(responses.story1)
    val expectedLoc: String = cache.location + id + ".json"

    assert(Files.exists(Paths.get(expectedLoc)))
    deleteAddedFile(id.toString)
  }

  test("Add user") {
    cache.addUser(responses.user)
    val id: String = json.getuserID(responses.user)
    val expectedLoc: String = cache.location + id + ".json"

    assert(Files.exists(Paths.get(expectedLoc)))
    deleteAddedFile(id)
  }

  test("File exists and is valid") {
    cache.addItem(responses.story1)
    val id: Long = json.getID(responses.story1)

    assert(cache.isValid(id.toString))
    deleteAddedFile(id.toString)
  }

  test("File does not exist is not valid") {
    assert(!cache.isValid("fakeID"))
  }

  test("File within ttl is valid") {
    val newCache: Cache = Cache(ttl = 100000)
    newCache.addItem(responses.story1)
    val id: Long = json.getID(responses.story1)

    // time within ttl
    assert(newCache.isValid(id.toString))
    deleteAddedFile(id.toString)
  }

  test("File out of ttl is not valid") {
    val newCache: Cache = Cache(ttl = 1)
    newCache.addItem(responses.story1)
    val id: Long = json.getID(responses.story1)

    Thread.sleep(1000)
    assert(!newCache.isValid(id.toString))
    deleteAddedFile(id.toString)
  }

  test("Clear cache") {
    val newCache: Cache = Cache(ttl = 1)
    newCache.addItem(responses.story1)

    val expectedLoc: String = cache.location
    val dir = new File(expectedLoc)

    val prevLen = dir.listFiles().length
    cache.clear()
    val newLen = dir.listFiles().length

    assert(newLen == 0)
    assert(prevLen > newLen)

  }

}
