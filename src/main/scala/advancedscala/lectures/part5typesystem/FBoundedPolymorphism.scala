package advancedscala.lectures.part5typesystem

object FBoundedPolymorphism extends App {

//  trait Animal {
//    def breed: List[Animal]
//  }
//
//  class Cat extends Animal {
//    override def breed: List[Animal] = ???
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Animal] = ???
//  }


//  BAD - depends on good programming practices but not compiler
//  trait Animal {
//    def breed: List[Animal]
//  }
//
//  class Cat extends Animal {
//    override def breed: List[Cat] = ???
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Dog] = ???
//  }

//  trait Animal[A <: Animal[A]] {
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Animal[Cat]] = ???
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Dog] = ???
//  }
//
//  class Crocodile extends Animal[Dog] {
//    override def breed: List[Dog] = ???
//  }

//  trait Animal[A <: Animal[A]] { self: A =>
//    def breed: List[Animal[A]]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Animal[Cat]] = ???
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Dog] = ???
//  }
//
//  trait Fish extends Animal[Fish]
//  class Shark extends Fish {
//    override def breed: List[Animal[Fish]] = ???
//  }



}
