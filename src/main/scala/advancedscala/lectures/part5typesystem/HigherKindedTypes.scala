package advancedscala.lectures.part5typesystem

import scala.collection.immutable.ListSet
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object HigherKindedTypes extends App {

  trait AHigherKindedType[F[_]]

  trait MyList[T] {
    def flatMap[B](f: T => B): MyList[B]
  }

  trait MyOption[T] {
    def flatMap[B](f: T => B): MyOption[B]
  }

  trait MyFuture[T] {
    def flatMap[B](f: T => B): MyFuture[B]
  }

  def multiply[A, B](listA: List[A], listB: List[B]): List[(A,B)] =
    for {
      a <- listA
      b <- listB
    } yield (a,b)

  def multiply[A, B](optionA: Option[A], optionB: Option[B]): Option[(A,B)] =
    for {
      a <- optionA
      b <- optionB
    } yield (a,b)

  def multiply[A, B](futureA: Future[A], futureB: Future[B]): Future[(A,B)] =
    for {
      a <- futureA
      b <- futureB
    } yield (a,b)

  trait Monad[F[_], A] {
    def flatMap[B](f: A => F[B]): F[B]
    def map[B](f: A => B): F[B]
  }

  class MonadList[A](list: List[A]) extends Monad[List, A] {
    override def flatMap[B](f: A => List[B]): List[B] = list.flatMap(f)
    override def map[B](f: A => B): List[B] = list.map(f)
  }

  def multiply[F[_], A, B](ma: Monad[F, A], mb: Monad[F,B]) =
    for {
      a <- ma
      b <- mb
    } yield (a,b)

  val monadList = new MonadList(List(1,2,3))
  monadList.flatMap(x => List(x, x + 1))

  println(multiply(new MonadList(List(1,2,3)), new MonadList(List("1","2","3"))))

}
