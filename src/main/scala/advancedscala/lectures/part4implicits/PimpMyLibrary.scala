package advancedscala.lectures.part4implicits

object PimpMyLibrary extends App {

  implicit class RichInt(value: Int) {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
    def times(function: => Unit):Unit = {
      (1 to value).foreach(_ => function)
    }
  }

  println(10.sqrt)

  1 to 10


  implicit class RichString(val string: String) extends AnyVal {
    def asInt:Int = Integer.valueOf(string)
  }

  println("10".asInt)
  10 times(println(("Hello")))

  implicit def stringToInt(string: String): Int = Integer.valueOf(string)
  println("6" / 2)

}
