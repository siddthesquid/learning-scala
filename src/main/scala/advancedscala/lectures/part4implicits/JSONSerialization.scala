package advancedscala.lectures.part4implicits

import java.util.Date

object JSONSerialization extends App {

  case class User(name: String, age: Int, email: String)
  case class Post(content: String, createdAt: Date)
  case class Feed(user: User, posts: List[Post])

  sealed trait JSONValue {
    def stringify: String
  }
  final case class JSONString(value: String) extends JSONValue {
    override def stringify: String = s"\"$value\""
  }
  final case class JSONNumber(value: Int) extends JSONValue {
    override def stringify: String = value.toString
  }
  final case class JSONArray(values: List[JSONValue]) extends JSONValue {
    override def stringify: String = values.map(_.stringify).mkString("[", ",", "]")
  }
  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue {
    override def stringify: String = values.map {
      case (key, value) => s"\"$key\": ${value.stringify}"
    }.mkString("{", ",", "}")
  }

  val data = JSONObject(
    Map(
      "user" -> JSONString("Sidd"),
      "posts" -> JSONArray(
        List(
          JSONString("hi"),
          JSONString("Bye"),
          JSONNumber(10),
        )
      )
    )
  )

  println(data.stringify)

  trait JSONConverter[T] {
    def convert(value: T): JSONValue
  }
  implicit class JSONOps[T](value: T) {
    def toJSON(implicit converter: JSONConverter[T]) = converter.convert(value)
  }
  implicit object StringConverter extends JSONConverter[String] {
    override def convert(value: String): JSONValue = JSONString(value)
  }
  implicit object NumberConverter extends JSONConverter[Int] {
    override def convert(value: Int): JSONValue = JSONNumber(value)
  }
  implicit object UserConverter extends JSONConverter[User] {
    override def convert(user: User): JSONValue = JSONObject(
      Map(
        "name" -> JSONString(user.name),
        "age" -> JSONNumber(user.age),
        "email" -> JSONString(user.email),
      )
    )
  }
  implicit object PostConverter extends JSONConverter[Post] {
    override def convert(post: Post): JSONValue = JSONObject(
      Map(
        "content" -> JSONString(post.content),
        "created" -> JSONString(post.createdAt.toString)
      )
    )
  }
  implicit object FeedConverter extends JSONConverter[Feed] {
    override def convert(feed: Feed): JSONValue = JSONObject(
      Map(
        "user" -> feed.user.toJSON,
        "posts" -> JSONArray(feed.posts.map(_.toJSON)),
      )
    )
  }

  val now = new Date(System.currentTimeMillis())
  val john = User("john", 34, "john@rockthejvm.com")
  val feed = Feed(john, List(
    Post("hello", now),
    Post("bye", now)
  ))

  println(feed.toJSON.stringify)


}
