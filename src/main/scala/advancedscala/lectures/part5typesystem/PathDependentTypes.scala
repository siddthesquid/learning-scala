package advancedscala.lectures.part5typesystem

object PathDependentTypes extends App {

  class Outer {
    class Inner
    object InnerObject
    type InnerType
    def printGeneral(i: Outer#Inner) = println(i)
  }

  def aMethod: Int = {
    class HelperClass
    type HelperType = String
    2
  }

  val outer = new Outer
  val inner = new outer.Inner
  val oo = new Outer
  (new Outer).printGeneral(inner)

  /**
   * Exercise
   * DB keyed by Int or String, but maybe others
   */

  trait Item {
    type ItemType
  }
  trait IntItem extends Item {
    override type ItemType = Int
  }
  trait StringItem extends Item {
    override type ItemType = String
  }

  def get[ItemType <: Item](item: (ItemType#ItemType)) = {}
  get[IntItem](52)


}
