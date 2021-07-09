package advancedscala.lectures.part4implicits

object TypeClasses extends App {
  trait HtmlWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HtmlWritable {
    override def toHtml: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }

  val john = User("John", 32, "john@rockthejvm")


  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User) = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  import java.util.Date
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>${date.toString}</div>"
  }

  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User) = s"<div>${user.name} <a href=${user.email}/> </div>"
  }


  // implicit
  object HTMLSerializer {
    def serializable[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style color=blue>$value</div>"
    def booboo = 10
  }

  println(HTMLSerializer.serializable(42))

  val users = List(
    User("a", 10, "a"),
    User("b", 10, "a"),
    User("b", 10, "b"),
  )
  implicit class HtmlEnrichment[T](value: T) {
    def toHtml(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }

  println(john.toHtml)


}
