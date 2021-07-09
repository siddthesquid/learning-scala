package advancedscala.lectures.part1as

import scala.annotation.tailrec

object Recap extends App {

  val aCondition: Boolean = false
  val aConditionVal = if (aCondition) 42 else 65

  // instructions vs expressions
  val aCodeBlock = {
    if (aCondition) 54
    56
  }

  // Unit
  val theUnit = println("hello scala")

  def aFunction(x: Int): Int = x + 1

  @tailrec def factorial(n:Int, accumulator:Int):Int =
    if (n <= 0) accumulator
    else factorial(n - 1, n * accumulator)

  class Animal
  class Dog extends Animal
  val aDog: Animal = new Dog()

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch")
  }

  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar")
  }

  // generics
  abstract class MyCovariantList[+A]
  abstract class MyInvariantList[A]
  type CovariantAnimalList = MyCovariantList[Animal]
  class CovariantDogList extends MyCovariantList[Dog]
  class InvariantAnimalList extends MyInvariantList[Animal]
  class InvariantDogList extends MyInvariantList[Dog]

  val cal1: CovariantAnimalList = new CovariantDogList

  // singleton objects and companions
  object MyList


  case class Person(name: String, age: Int)

  // exceptions

//  val throwsException = throw new RuntimeException // Nothing
  val aPotentialFailure = try {
    throw new RuntimeException
  } catch {
    case e:RuntimeException => "caught runtime exception"
    case _ => "blah"
  } finally {
    println("some logs")
  }

  val incrementer = new Function1[Int,Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x:Int) => x + 1
  anonymousIncrementer.apply(10)
  ((x:Int) => x + 1).apply(10)

  List(1,2,3).map(incrementer)

  val pairs = for {
    num <- List(1,2,3) if num % 2 == 0
    char <- List('a','b','c')
  } yield s"$num-$char"
  pairs foreach println

  val aMap = Map(
    "Sidd" -> 23,
    "Yoyo" -> 44,
  )

  aMap("Sidd")

  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "Second"
    case 3 => "Third"
    case _ => x + "th"
  }


}
