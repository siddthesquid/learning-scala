package catscourse.lectures.part2abstractMath

import scala.util.Try

object Functors extends App {

  val aModifiedList = List(1,2,3).map(_ + 1)

  trait MyFunctor[F[_]] {
    def map[A, B](initialValue: F[A])(f: A => B): F[B]
  }

  import cats.Functor
  import cats.instances.list._
  val listFunctor = Functor[List]

  val incrementedNumbers = listFunctor.map(List(1,2,3))(_ + 1)

  import cats.instances.option._
  val optionFunctor = Functor[Option]
  val incrementedOption = optionFunctor.map(Option(2))(_ + 1)

  import cats.instances.try_._
  val anIncrementedTry = Functor[Try].map(Try(42))(_ + 1)


  import cats.syntax.functor._
  def doF[F[_] : Functor, A, B](list: F[A])(f: A => B): F[B] = list.map(f)




  trait Tree[+T]
  case class Leaf[+T](value: T) extends Tree[T] {
    override def toString: String = s"{$value}"
  }
  case class Branch[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
    override def toString: String = s"{$value, $left, $right}"
  }


  implicit object TreeFunctor extends Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case leaf: Leaf[A] => Leaf(f(leaf.value))
      case branch: Branch[A] => Branch(f(branch.value), map(branch.left)(f), map(branch.right)(f))
    }
  }

  val someTree: Tree[Int] = Branch(10, Branch(20, Leaf(3), Leaf(4)), Leaf(5))
  val mappedTree = doF(someTree)(_ * 2)
  println(someTree)
  println(mappedTree)


  val tree: Tree[Int] = Branch(10, Leaf(20), Leaf(30))
  val incrementedTree = tree.map(_ * 2)




}
