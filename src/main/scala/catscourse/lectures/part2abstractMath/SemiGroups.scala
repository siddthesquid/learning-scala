package catscourse.lectures.part2abstractMath

object SemiGroups extends App {

  // Semigroups COMBINE elements of the same type
  import cats.Semigroup
  import cats.instances.int._
  import cats.instances.string._
  import cats.instances.option._

  val naturalStringSemiGroup = Semigroup[String]
  println(naturalStringSemiGroup.combine("helo", "bye"))

  def reduce[T](list: List[T])(implicit semigroup: Semigroup[T]): T = list.reduce(semigroup.combine)

  println(reduce((1 to 10).toList))

  import cats.syntax.semigroup._
  println("Hello" |+| "fda")

  println(reduce(List(Option(10), Option(20), None, Option(3))))
  println(reduce(List(Option.empty[Int])))

  case class Expense(id: Long, amount: Double)

  implicit val expenseSemigroup: Semigroup[Expense] = Semigroup.instance { (expense1, expense2) =>
    Expense(math.max(expense1.id, expense2.id), expense1.amount + expense2.amount)
  }

  println(
    reduce(
      List(
        Option(Expense(10, 100)),
        None,
        Option(Expense(20, 300))
      )
    )
  )

  def reduceThings[T : Semigroup](list: List[T]): T = list.reduce((a,b) => a |+| b)

}
