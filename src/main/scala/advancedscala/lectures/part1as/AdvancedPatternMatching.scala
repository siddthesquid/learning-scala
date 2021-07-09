package advancedscala.lectures.part1as

object AdvancedPatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"The only element is $head")
    case _ => ???
  }

  class Person(val name: String, val age:Int)
  object Person {
    def unapply(person: Person): Option[(String, Int)] = if (person.age < 21) return None else Some((person.name, person.age))
    def unapply(age: Int): Option[String] = Some(s"i am $age years old")
  }

  val bob = new Person("bob", 20)
  println {
    bob match {
      case Person(name: String, age: Int) => s"$name is $age years old"
      case _ => "not a person"
    }
  }

  println {
    bob.age match {
      case Person(status) => println(status)
    }
  }

  trait Blah
  object singleDigit {
    def unapply(a: Int) = if (a < 10) Some(true) else None
  }
  object even {
    def unapply(a: Int) = if (a % 2 == 0) Some(true) else None
  }
  object multipleOf3 {
    def unapply(a: Int) = if (a % 3 == 0) Some(true) else None
  }

  val n:Int = 45
  val mathProperty = n match {
    case singleDigit(n) => "single digit"
    case even(n) => "even"
    case multipleOf3(n) => "multipleOf3"
  }
  println()


  case class Or[A, B](a: A, b: B)
  val either = Or(2, "two")
  val humanDescription = either match {
    case number Or string => number
  }

  val vararg = numbers match {
    case List(1, _*) =>
  }

  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }

  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] =
      if (list == Empty) Some(Seq.empty)
      else unapplySeq(list.tail).map(list.head +: _)
  }

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person:Person): Wrapper[(String,Int)] = new Wrapper[(String,Int)] {
      override def get: (String,Int) = (person.name, person.age)
      override def isEmpty: Boolean = person.name == "bob"
    }
  }

  val b = new Person("bob", 10)
  b match {
    case PersonWrapper(name) => println("helo")
    case _ => println("Bye")
  }
  {
    for {
      age <- List(5,6,7)
      name <- List("bob", "mary")
    } yield new Person(name, age)
  }.map {
    case PersonWrapper((name,age)) => println(s"helo $age")
    case _ => println("Bye")
  }

}
