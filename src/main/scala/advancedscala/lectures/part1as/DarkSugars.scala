package advancedscala.lectures.part1as

import scala.util.Try

object DarkSugars extends App {

  // syntax sugar #1 methods with single param
  def singleArgMethod(arg: Int):String = s"$arg little ducks..."
  val description = singleArgMethod {
    // code
    42
  }

//  val aTry = Try {
//    throw new RuntimeException
//  }

  List(1,2,3).map { x =>
    x + 1
  }

  // syntax sugar #2: single abstract method pattern
  trait Action {
    def act(x: Int): Int
  }

  val anInstance:Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val aFunkyInstace:Action = (x:Int) => x + 1

  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hi")
  })

  val aSweeterThread = new Thread(() => println("Sweet Scala"))

  abstract class AnAbstractType {
    def implemented:Int = 23
    def f(a: Int): Unit
  }

  val anAbstractInstance: AnAbstractType = (a:Int) => println("sweet")

  // syntax sugar #3
  val prependedList = 2 :: List(3,4)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream

  class TeenGirl(name: String) {
    def `and then said` (gossip: String) { println(s"$name said $gossip")}
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet"

  class Composite[A, B]
//  val composite: Composite[Int, String] = ???
//  val composite2: Int Composite String = ???

  class -->[A, B]
//  val toward:Int --> String = ???

  val anArray = Array(1,2,3)
  anArray(2) = 7
  //

  class Thing(var hello:Int = 10) {
    def update(a:Int, b:Int, c:Int): Unit = {
      hello  = a + b + c
    }
  }
  val t = new Thing()
  t(2,3) = 4
  println(t.hello)


  class Mutable {
    private var internalMember: Int = 0
    def member = internalMember
    def member_=(value:Int): Unit = internalMember = value
  }

  val m = new Mutable
  m.member = 42



}
