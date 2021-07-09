package advancedscala.lectures.part2afp

import scala.annotation.tailrec

object LazyEvaluation extends App {

  lazy val x: Int = {
    println("hello")
    42
  }

  println(x)
  println(x)

  def byNameMethod(n: => Int):Int = {
    val t = n
    t + t + t
  }

  def retrieveVal:Int = {
    println("hello")
    42
  }

  println(byNameMethod(retrieveVal))


  val a = List(30, 20, 50, 100)
    .withFilter { x =>
      println("filter 1")
      x > 25
    }
    .withFilter { x =>
      println("filter 2")
      x < 80
    }

  a.foreach(println)

  // Lazily evaluted single linked stream of elements
  abstract class MyStream[+A] {
    def isEmpty: Boolean
    def head: A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B]
    def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B]

    def foreach(f: A => Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean): MyStream[A]

    def take(n:Int): MyStream[A]
    def takeAsList(n: Int): List[A]
  }

  class EmptyStream[+A] extends MyStream[A] {
    override def isEmpty: Boolean = true
    override def head: A = throw new IndexOutOfBoundsException
    override def tail: MyStream[A] = throw new IndexOutOfBoundsException

    override def #::[B >: A](element: B): MyStream[B] = new Cons[B](element, this)
    override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = anotherStream

    override def foreach(f: A => Unit): Unit = ()
    override def map[B](f: A => B): MyStream[B] = new EmptyStream[B]
    override def flatMap[B](f: A => MyStream[B]): MyStream[B] = new EmptyStream[B]
    override def filter(predicate: A => Boolean): MyStream[A] = this

    override def take(n: Int): MyStream[A] = if (n == 0) this else throw new IndexOutOfBoundsException
    override def takeAsList(n: Int): List[A] = if (n == 0) Nil else throw new IndexOutOfBoundsException
  }

  class Cons[+A](h: A, t: => MyStream[A]) extends MyStream[A] {
    override def isEmpty: Boolean = false
    override val head: A = h
    override lazy val tail: MyStream[A] = t

    override def #::[B >: A](element: B): MyStream[B] = new Cons[B](element, this)
    override def ++[B >: A](anotherStream: => MyStream[B]): MyStream[B] = new Cons[B](head, tail ++ anotherStream)

    override def foreach(f: A => Unit): Unit = {
      f(head)
      tail.foreach(f)
    }
    override def map[B](f: A => B): MyStream[B] = new Cons[B](f(head), tail.map(f))
    // override def map[B](f: A => B): MyStream[B] = f(head) #:: tail.map(f)
    override def flatMap[B](f: A => MyStream[B]): MyStream[B] = f(head) ++ tail.flatMap(f)
    override def filter(predicate: A => Boolean): MyStream[A] = {
      if (predicate(h)) new Cons(h, tail.filter(predicate))
      else {
        println(s"hi im $head")
        tail.filter(predicate)
      }
    }

    override def take(n: Int): MyStream[A] = if (n==0) new EmptyStream else head #:: tail.take(n-1)
    override def takeAsList(n: Int): List[A] = if (n==0) Nil else head :: tail.takeAsList(n-1)
  }

  object MyStream {
    def from[A](start: A)(generator: A => A): MyStream[A] = new Cons[A](start, from(generator(start))(generator))
  }

  MyStream.from(1)(_ + 1).filter(_ > 10)

  /*
  val fromA = MyStream.from(1)((_:Int) + 1).take(10)
  val fromB = fromA ++ MyStream.from(1)((_:Int) * 2).take(30)
  fromB.map { x =>
    x * 2
  }.flatMap { x:Int =>
    x #:: (x / 2) #:: new EmptyStream
  }.filter {
    _ < 1000000
  }.takeAsList(10).foreach(println)

  // fibonacci numbers
  // 1 1 2 3 5 8 13 21 34 ...
  val stream = 1 #:: MyStream.from((0,1)) {
    case (e1, e2) => (e2, e1+e2)
  }.map { case (e1,e2) => e1 + e2 }
  stream.take(10).foreach(println)

  // prime numbers
  // 2 3 5 7 11 ...
  val from2 = MyStream.from(2)(_ + 1)
  def filterHead(s:MyStream[Int]):MyStream[Int] = {
    new Cons[Int](s.head, filterHead(s.tail.filter(_ % s.head != 0)))
  }

  val primes = filterHead(from2)
  primes.take(10).foreach(println)


*/



}
