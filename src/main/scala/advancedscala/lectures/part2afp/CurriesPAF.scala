package advancedscala.lectures.part2afp

object CurriesPAF extends App {

  val superAdder: Int => Int => Int = x => y => x + y

  val add3 = superAdder(3)
  println(add3(5))
  println(superAdder(3)(5))

  def curriedAdder(x: Int)(y: Int): Int = x + y
  val add4: Int => Int = curriedAdder(4)
  val thing = curriedAdder(_:Int)(_:Int)

  // Partial function applications
  val add5 = curriedAdder(5) _

  // Exercises
  val simpleAddFunction = (x:Int, y:Int) => x + y
  def simpleAddMethod(x:Int, y:Int) = x + y
  def curriedAddMethod(x:Int)(y:Int) = x + y

  // add7: Int => Int
  val add7a = curriedAddMethod(7) _
  val add7b = simpleAddMethod(7, _)
  val add7c = simpleAddFunction(7,_)
  val add7d:Int => Int = curriedAddMethod(7)
  val add7e = (x: Int) => simpleAddFunction(7, x)
  val add7f = simpleAddFunction.curried(7)

  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I am ", _, ". How are you?")
  println(insertName("sidd"))

  val fillInTB = concatenator("hello", _, _)
  fillInTB("Hi",_)

  // exercises

  // process a list of numbers and return their string representations with different formats

  val l = List(1,2,3)
  val formats = Seq("%4.2f", "%8.6f", "%14.12f")
  val formatter = (_:String).format(_:Double)
  val formatters = formats.map(formatter.curried)
  println(l.flatMap(num => formatters.map(_(num))))

  def byName(n: => Int):Int = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method:Int = 42
  def parenMethod():Int = 42

  byName(10)
  byFunction(() => 20)
  byName(method)
  byFunction(method _)
  byName(parenMethod())
  byFunction(parenMethod)


}
