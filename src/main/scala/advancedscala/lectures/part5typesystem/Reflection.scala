package advancedscala.lectures.part5typesystem

object Reflection extends App {

  // reflection

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"hello my name is $name")
  }

  import scala.reflect.runtime.{universe => ru}

  // mirror
  val m = ru.runtimeMirror(getClass.getClassLoader)

  // create class object (description)
  val clazz = m.staticClass("lectures.part5typesystem.Reflection.Person")

  // make reflected mirror (object)
  val cm = m.reflectClass(clazz)

  // get constructor
  val constructor = clazz.primaryConstructor.asMethod

  // reflect constructor
  val constructorMirror = cm.reflectConstructor(constructor)

  //invoke constructor
  val instance = constructorMirror("john")

  println(instance)

  // i have an instance
  val p = Person("Mary")

  // method name computed from elsewhere
  val methodName = "sayMyName"

  // reflect instance
  val reflected = m.reflect(p)

  // method symbol
  val methodSymbol = ru.typeOf[Person].decl(ru.TermName(methodName)).asMethod

  // reflect the method
  val method = reflected.reflectMethod(methodSymbol)
  method()

  // type erasure
  val numbers = List(1,2,3)
  numbers match {
    case listOfStrings: List[String] => println("list of strings")
    case listOfStrings: List[Int] => println("list of ints")
  }

  // pp #2
//  def processList(list: List[Int]): Int = 43
//  def processList(list: List[String]):Int = 45

  import ru._
  val ttag = typeTag[Person]








}
