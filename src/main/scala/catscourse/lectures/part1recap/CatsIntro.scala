package catscourse.lectures.part1recap

object CatsIntro {

  // Eq
  val aComparison = 2 == "a string"

  // type class import
  import cats.Eq

  // part 2 - import TC instances for the types you need
  import cats.instances.int._

  // use TC API
  val intEquality = Eq[Int]
  intEquality.eqv(2,3)

  // use extension methods if applicable
  import cats.syntax.eq._
  2 === 3

  // extended TC operations to composite types, e.g. Lists
  import cats.instances.list._
  List(2) === List(3)

  // what if our type is not supported
  case class ToyCar(model: String, price: Double)
  implicit val toyCarEq: Eq[ToyCar] = Eq.instance { (car1, car2) =>
    car1.price == car2.price
  }


  ToyCar("Honda", 1000) === ToyCar("Toyota", 1000)




  def main(args: Array[String]): Unit = {

  }

}
