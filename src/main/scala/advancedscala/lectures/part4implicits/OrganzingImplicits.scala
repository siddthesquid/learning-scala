package advancedscala.lectures.part4implicits

object OrganzingImplicits extends App {

  case class Person(name: String, age: Int)
  val people = List(
    Person("Alice", 20),
    Person("Bob", 30),
    Person("Eve", 25)
  )
  object Person {
    implicit val ordered: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }
  implicit val ordered: Ordering[Person] = Ordering.fromLessThan(_.name > _.name)
  println(people.sorted)

  class Blah(val x:Int) {

  }

  class TestThing {
    def addToBase(i: Int)(implicit j: Blah):Int = i + j.x
  }

  object Blah {
    implicit val k: Blah = new Blah(10)
  }

  val a = new TestThing()
  a.addToBase(10)

  case class Purchase(nUnits: Int, unitPrice: Double)

  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan { (p1, p2) =>
      p1.nUnits * p1.unitPrice < p2.nUnits * p2.unitPrice
    }
    object UnitPriceOrdering {
      implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
    }
    object NumUnitsOrdering {
      implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
    }
  }

  val purchases = List(
    Purchase(10, 10.0),
    Purchase(15, 5.00),
    Purchase(13, 1.00),
    Purchase(2, 7.00)
  )

  println(purchases.sorted)

  import Purchase.NumUnitsOrdering._
  println(purchases.sorted)

  println(purchases.sorted(Purchase.UnitPriceOrdering.totalPriceOrdering))




}
