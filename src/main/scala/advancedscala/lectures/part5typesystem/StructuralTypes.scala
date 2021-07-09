package advancedscala.lectures.part5typesystem

object StructuralTypes extends App {

  // structural types
  type JavaCloseable = java.io.Closeable

  class HipsterCloseable {
    def close(): Unit = println("yea yea im closing")
    def closeSilently(): Unit = println("hello")
  }

  type UnifiedCloseable = {
    def close(): Unit
  }

  def closeQuietly(unifiedCloseable: UnifiedCloseable):Unit = unifiedCloseable.close()

  closeQuietly(new JavaCloseable {
    override def close(): Unit = {}
  })

  closeQuietly(new HipsterCloseable)


  // type refinements

  type AdvancedCloseable = JavaCloseable {
    def closeSilently(): Unit
  }

  class AdvancedJavaCloseable extends JavaCloseable {
    override def close(): Unit = println("java closes")
    def closeSilently(): Unit = println("java closes silently")
  }

  def closeShh(advancedCloseable: AdvancedCloseable): Unit = {
    advancedCloseable.close()
    advancedCloseable.closeSilently()
  }


  def altClose(closeable: { def close(): Unit}) = {}
  def typedAltClose[T <: {def close(): Unit}](closeable: T) = closeable.close()


  type SoundMaker = {
    def makeSound(): Unit
  }

  class Dog {
    def makeSound(): Unit = println("bark")
  }

  class Car {
    def makeSound(): Unit = println("vroom")
  }

  val dog:SoundMaker = new Dog
  val car:SoundMaker = new Car

  /**
   * Exercise
   */

  trait CBL[+T] {
    def head: T
    def tail: CBL[T]
  }

  class Human {
    def head: Brain = new Brain
  }

  class Brain {
    override def toString: String = "brainz"
  }

  def f[T](somethingWithAHead: {def head: T}): Unit = println(somethingWithAHead.head)
  f[Int](new CBL[Int] {
    override def head: Int = 10
    override def tail: CBL[Int] = this
  })
  f[Brain](new Human)


  /**
   * 2
   */

  object HeadEqualizer {
    type Headable[T] = { def head: T }
    def ===[T](a: Headable[T], b: Headable[T]): Boolean = a.head == b.head
  }

  println(HeadEqualizer.===(new CBL[Int]{
    override def head: Int = 10
    override def tail: CBL[Int] = this
  },
  new CBL[Int]{
    override def head: Int = 10
    override def tail: CBL[Int] = this
  }))

  val human = new Human
  println(HeadEqualizer.===(human, human))

}
