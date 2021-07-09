package advancedscala.lectures.part2afp

object Monads extends App {

  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]):Attempt[B]
  }

  object Attempt {
    def apply[A](a: => A): Attempt[A] = {
      try {
        Success(a)
      } catch {
        case e:Throwable => Fail(e)
      }
    }
  }

  case class Success[+A](value: A) extends Attempt[A] {
    override def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try {
        f(value)
      } catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    override def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = this
  }

  val attempt = Attempt {
    throw new RuntimeException("my own monad")
  }
  println(attempt)

  // Lazy[T] Monad
  /*
  val l = Lazy(thing)
  thing.flatMap(lazy
   */

  class Lazy[+A](value: => A) {
    def use: A = value
    def flatMap[B](f: A => Lazy[B]): Lazy[B] = f(value)
  }
  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value)
  }

  // map + flatten from
  trait Monad[+A] {
    def unit[B](a: B): Monad[B]
    def flatMap[B](f: A => Monad[B]): Monad[B]
    def map[B](f: A => B): Monad[B] = flatMap(f.andThen(unit))
    def flatten = flatMap(unit)
  }

  val lazyI = Lazy {
    println("hi")
    52
  }
  println(lazyI.use)
  println(lazyI.use)



}
