package advancedscala.exercises

import scala.annotation.tailrec

trait MySet[A] extends (A => Boolean) {

  override def apply(elem: A): Boolean = contains(elem)

  def contains(elem: A): Boolean
  def +(elem: A): MySet[A]
  def ++(anotherSet: MySet[A]): MySet[A]

  def map[B](f: A => B): MySet[B]
  def flatMap[B](f: A => MySet[B]): MySet[B]
  def filter(predicate: A => Boolean): MySet[A]
  def foreach(f: A => Unit): Unit

  def -(elem: A): MySet[A]
  def &(anotherSet: MySet[A]): MySet[A]
  def --(anotherSet: MySet[A]): MySet[A]

  def unary_! : MySet[A]

}

class EmptySet[A] extends MySet[A] {
  override def contains(elem: A): Boolean = false
  override def +(elem: A): MySet[A] = new NonEmptySet[A](elem, this)
  override def ++(anotherSet: MySet[A]): MySet[A] = anotherSet

  override def map[B](f: A => B): MySet[B] = new EmptySet[B]
  override def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
  override def filter(predicate: A => Boolean): MySet[A] = this
  override def foreach(f: A => Unit): Unit = {}

  def -(elem: A) = this
  def &(anotherSet: MySet[A]) = this
  def --(anotherSet: MySet[A]) = this

  override def unary_! : MySet[A] = new PropertyBasedSet[A](_ => true)
}

class PropertyBasedSet[A](property: A => Boolean) extends MySet[A] {
  override def contains(elem: A): Boolean = property(elem)
  override def +(elem: A): MySet[A] = new PropertyBasedSet[A](e => property(e) || elem == e)
  override def ++(anotherSet: MySet[A]): MySet[A] = new PropertyBasedSet[A](e => property(e) || anotherSet(e))

  override def map[B](f: A => B): MySet[B] = politelyFail
  override def flatMap[B](f: A => MySet[B]): MySet[B] = politelyFail
  override def filter(predicate: A => Boolean): MySet[A] = new PropertyBasedSet[A](e => property(e) && predicate(e))
  override def foreach(f: A => Unit): Unit = politelyFail

  override def -(elem: A): MySet[A] = filter(elem != _)
  override def &(anotherSet: MySet[A]): MySet[A] = filter(anotherSet)
  override def --(anotherSet: MySet[A]): MySet[A] = filter(!anotherSet)

  override def unary_! : MySet[A] = new PropertyBasedSet[A](e => !property(e))

  def politelyFail = throw new IllegalArgumentException("Really deep rabbit hole!")
}

class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A] {
  override def contains(elem: A): Boolean = elem == head || tail.contains(elem)
  override def +(elem: A): MySet[A] =
    if (contains(elem)) this
    else new NonEmptySet[A](elem, this)
  override def ++(anotherSet: MySet[A]): MySet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): MySet[B] =
    (tail.map(f)) + f(head)
  override def flatMap[B](f: A => MySet[B]): MySet[B] =
    f(head) ++ tail.flatMap(f)
  override def filter(predicate: A => Boolean): MySet[A] = {
    if (predicate(head)) (tail.filter(predicate)) + head
    else (tail.filter(predicate))
  }

  override def foreach(f: A => Unit): Unit = {
    f(head)
    tail.foreach(f)
  }

  def -(elem: A) = filter(_ != elem)
  def &(anotherSet: MySet[A]) = filter(anotherSet)
  def --(anotherSet: MySet[A]) = filter(!anotherSet(_))

  override def unary_! : MySet[A] = new PropertyBasedSet[A](x => !contains(x))
}

object MySet {
  def apply[A](values: A*): MySet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
      if (valSeq.isEmpty) acc
      else buildSet(valSeq.tail, acc + valSeq.head)
    }
    buildSet(values, new EmptySet[A])
  }
}

object MySetPlayGround extends App {
  val s = MySet(1,2,3,4)
  s + 5 ++ MySet(-2) + 3 flatMap (x => MySet(x, x*10)) filter (_ % 2 == 0) foreach println
  val negative = !s
  Seq(
    negative.contains(4),
    negative.contains(5),
  ).foreach(println)
}