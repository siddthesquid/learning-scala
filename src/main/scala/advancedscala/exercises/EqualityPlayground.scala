package advancedscala.exercises

import advancedscala.lectures.part4implicits.TypeClasses.User
import advancedscala.lectures.part4implicits.TypeClasses.User

object EqualityPlayground extends App {

  trait Equal[T] {
    def ====(me: T, other: T): Boolean
  }

  object Equal {
    def ====[T](me: T, other: T)(implicit equal: Equal[T]) = equal.====(me, other)
  }

  implicit class Equaler[T](value: T) {
    def ====(other: T)(implicit equalizer: Equal[T]) = Equal.====(value, other)
    def !===(other: T)(implicit equalizer: Equal[T]) = !Equal.====(value, other)
  }

  implicit object NameEquality extends Equal[User] {
    override def ====(me: User, other: User): Boolean = me.name == other.name
  }

  object FullEquality extends Equal[User] {
    override def ====(me: User, other: User): Boolean = me.name == other.name && me.email == other.email
  }

  implicit object IntEquality extends Equal[Int] {
    override def ====(me: Int, other: Int): Boolean = me == other
  }

  println(10 ==== 20)
  println(10 !=== 20)
  println(User("Sidd", 10, "f") ==== User("Sidd", 30, ""))

  def myImplictly[T](implicit thing: T) = thing
  myImplictly[Equal[Int]]
  implicitly[Equal[Int]]

}
