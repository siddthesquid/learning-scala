package catscourse.lectures.part1recap

import advancedscala.lectures.part5typesystem.HigherKindedTypes

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object Essentials {

  val aBoolean: Boolean = false

  val anIfExpression: String = if (2 > 3) "bigger" else "smaller"

  val theUnit: Unit = println("Hello, scala")

  class Animal
  class Cat extends Animal
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(animal: Animal): Unit = println("Crunch")
  }

  object MySingletone

  object Carnivore

  class MyList[T]

  val three = 1 + 2
  val anotherThree = 1.+(2)

  val incrementer: Int => Int = (x: Int) => x + 1
  val incremented = incrementer(45)

  val processedList = List(1,2,3).map {
    incrementer
  }.flatMap { x =>
    List(x, x + 1)
  }
    .filter (_ % 2 == 0)

  val checkerBoard = List(1,2,3).flatMap { n=>
    List('a', 'b', 'c').map(c => (n,c))
  }

  val anotherCheckerBoard = for {
    n <- List(1,2,3)
    c <- List('a', 'b', 'c')
  } yield (n, c)

  val anOption = Option(3)
  val doubledOption = for {
    x <- anOption
  } yield 2 * x

  val anAttempt = Try(42)
  val modifiedAnAttempt = anAttempt.map(_ * 2)

  val anUnknown: Any = 45
  val ordinal = anUnknown match {
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }

  val optionDesc = anOption match {
    case Some(thing) => s"its $thing"
    case None => "empty"
  }

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val aFuture = Future(42)


  aFuture.onComplete {
    case Success(value) => println(s"the async meaning of life is $value")
    case Failure(exception) => println(s"meaning of life failed $exception")
  }

  val anotherFuture = aFuture.map(_ + 1)

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 43
    case 8 => 56
    case 100 => 999
  }

  trait HigherKindedType[F[_]]
  trait SequenceChecker[F[_]] {
    def isSequential: Boolean
  }




  def main(args: Array[String]): Unit = {

  }

}
