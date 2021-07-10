package catscourse.lectures.part2abstractMath

import java.util.concurrent.Executors
import scala.collection.View.FlatMap
import scala.concurrent.{ExecutionContext, Future}

object Monads extends App {

  val numbersList = List(1,2,3)
  val charsList = List('a', 'b', 'c')

  val combinationsFlatmap = numbersList.flatMap(n => charsList.map((n, _)))
  val combinationsFor = for {
    n <- numbersList
    c <- charsList
  } yield (n, c)
  println(combinationsFlatmap)
  println(combinationsFor)


  val numberOption = Option(2)
  val charOption:Option[Int] = None
  println(numberOption.flatMap(n => charOption.map(c => (n,c))))
  println( for {
    n <- numberOption
    c <- charOption
  } yield (n,c))

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val numberFuture = Future(42)
  val charFuture = Future('Z')


  trait MyMonad[M[_]] {
    def pure[A](value: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
    def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(f andThen pure)
  }

  import cats.Monad
  import cats.instances.option._ // Monad[Option]
  val optionMonad = Monad[Option]
  optionMonad.pure(10)
  val aTransformedOption = optionMonad.flatMap(optionMonad.pure(4)) (n => if (n % 3 == 0) Some(n) else None )

  import cats.instances.list._
  val listMonad = Monad[List]
  val aList = listMonad.pure(3)
  val aTransformedList = listMonad.flatMap(aList)(x => List(x, x+1))

  import cats.instances.future._
  val futureMonad = Monad[Future]
  val aFuture = futureMonad.pure(10)
  val aTransformedFuture = futureMonad.flatMap(aFuture)(x => Future(x + 1))

  def getPairsList(numbers: List[Int], chars: List[Char]): List[(Int,Char)] = numbers.flatMap(n => chars.map(c => (n,c)))


  import cats.syntax.flatMap._
  import cats.syntax.functor._
  def getPairs[M[_] : Monad,A,B](container1: M[A], container2: M[B])(implicit monad: Monad[M]): M[(A,B)] = monad.flatMap(container1)(c1 => monad.map(container2)(c2 => (c1, c2)))
  def getPairsFor[M[_] : Monad, A, B](ma: M[A], mb: M[B]): M[(A,B)] = for {
    c1 <- ma
    c2 <- mb
  } yield (c1, c2)



}
