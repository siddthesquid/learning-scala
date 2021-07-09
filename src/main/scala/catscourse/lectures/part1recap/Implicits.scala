package catscourse.lectures.part1recap

object Implicits {

  case class Person(name: String) {
    def greet: String = s"hi my name is $name"
  }

  implicit class ImpersonableString(name: String) {
    def greet: String = Person(name).greet
  }

  println("peter".greet)

  import scala.concurrent.duration._
  val oneSec = 1.second

  def increment(x: Int)(implicit amount: Int, amount2: Int) = x + amount
  implicit val defAmount = 2
  increment(10)
  increment(10)


  def main(args: Array[String]): Unit = {

  }

}
