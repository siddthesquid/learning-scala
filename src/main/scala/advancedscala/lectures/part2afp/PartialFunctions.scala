package advancedscala.lectures.part2afp

import scala.util.control.Breaks.break

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Function1[Int,Int] === Int => Int
  val aFussyFunction: Int => PartialFunction[Int,Int] = (x: Int) => {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }
  class FunctionNotApplicableException extends Exception

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  println(aPartialFunction(1))

  // PF Utilities
  println(aPartialFunction.isDefinedAt(67))

  // lift
  val lifted = aPartialFunction.lift // Int => Option(Int)
  List(1,2,3).map(lifted).foreach {
    case Some(num) => println(num)
    case None => println("ya dun goofed")
  }

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }.orElse[Int,Int] {
    case 100 => 200
  }

  println(pfChain(100))

  // PF extend normal functions

  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  val aMappedList = List(1,2,3).map {
    case 1 => 42
    case 2 => 41
    case 3 => 100
  }
  println(aMappedList)


  /*
   * Exercises
   */

  val anonPF = new PartialFunction[Int,Int] {
    override def isDefinedAt(x: Int): Boolean = List(1,2,3).contains(x)
    override def apply(v1: Int): Int = v1 * 2
  }.lift
  List(1,2,3,4).map(anonPF).foreach {
    case Some(value) => println(s"got $value")
    case None => println("Not in the domain.")
  }

  val chatBotPF:PartialFunction[String, String] = {
    case "hello" => "Hi"
    case "good?" => "good"
  }

  val chatBot = (message: String) => println {
    chatBotPF.lift(message) match {
      case Some(message) => message
      case None => "i dont understand you"
    }
  }

  scala.io.Source.stdin.getLines().map(chatBot).foreach(println)


}

