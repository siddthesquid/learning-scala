package advancedscala.lectures.part5typesystem

object TypeMembers extends App {

  class Animal
  class Dog extends Animal
  class Cat extends Animal

  class AnimalCollection {
    type AnimalType
    type BoundedAnimal <: Animal
    type SuperBoundedAnimal >: Dog <: Animal
    type AnimalC = Cat
  }

  val ac = new AnimalCollection
//  val cat: ac.BoundedAnimal = new Animal
  val pup: ac.SuperBoundedAnimal = new Dog
  val cat: ac.AnimalC = new Cat

  type CatAlias = Cat
  val anotherCat: CatAlias = new Cat

  trait MyList {
    type T
    def add(element: T): MyList
  }

  class NonEmptyList(value: Int) extends MyList {
    override type T = Int
    def add(element: Int): MyList = ???
  }

  type CatsType = cat.type
  val newCat: CatsType = cat

  /**
   * Exercise - enforce a type to be applicable to some types only
   */

  trait MList {
    type A
    def head: A
    def tail: MList
  }

  trait ApplicableToNumbers extends MList {
    type A <: Number
  }

  class CustomList(hd: String, tl: CustomList) extends MList {
    type A = String
    def head: String = hd
    def tail:CustomList = new CustomList(hd, tl)
  }

  class IntList(hd: Int, tl: IntList) extends MList {
    type A = Int
    def head = hd
    def tail = new IntList(hd, tl)
  }


}
