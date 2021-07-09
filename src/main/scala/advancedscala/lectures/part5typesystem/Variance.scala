package advancedscala.lectures.part5typesystem

import advancedscala.lectures.part1as.Recap.MyList

object Variance {

  class Animal
  class Cat extends Animal
  class Dog extends Animal
  class Croc extends Animal

  class CCage[+T]
  val a:CCage[Animal] = new CCage[Dog]

  class XCage[-T]
  val xcage:XCage[Dog] = new XCage[Animal]

  class InvariantCage[T](val animal: T)

  class CovariantCage[+T](val animal: T)

//  class ContravariantCage[+T](var animal: T)

//  class CovariantVariableCage[+T](var animal: T)

  class InvariantVariableCage[T](var animal: T)

  /*
//  trait AnotherCovariantCage[+T] {
//    def addAnimal[T](animal: T)
//  }
    val acc:AnotherCovariantCage[Animal] = new AnotherCovariantCage[Cat]
    acc.addAnimal(dog)

   */


  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true
  }
  class Kitty extends Cat
  val acc:AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  acc.addAnimal(new Kitty)


  class MyList[+A] {
    def add[B >: A](element: B): MyList[B] = new MyList[B]
  }
  val ml:MyList[Animal] = new MyList[Cat]
  ml.add(new Cat)
  ml.add(new Kitty)
  val ml2 = ml.add(new Dog)

  /**
   * Invariant, covariant, contravariant
   *
   * Parking[T](things: List[T]) {
   *  park(vehicle: T)
   *  impound(vehicles: List[T]
   *  checkVehicles(conditions: String): List[T]
   * }
   *
   * 2. Use IList
   * 3. monad, implement flatmap
   *
   *
   */

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle

  class IList[T]

  class InvariantParking[T](val things: List[T]) {
    def park(vehicle: T): InvariantParking[T] = ???
    def impound(vehicles: List[T]): InvariantParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???
    def flatMap[B](f: T => List[B]): InvariantParking[B] = new InvariantParking(things.flatMap(f))
  }

  class CovariantParking[+T](val things: List[T]) {
    def park[U >: T](vehicle: U): CovariantParking[U] = ???
    def impound[U >: T](vehicles: List[U]): CovariantParking[U] = ???
    def checkVehicles(conditions: String): List[T] = Nil
    def flatMap[U >: T, B](f: U => List[B]): CovariantParking[B] = new CovariantParking(things.flatMap(f))
  }

  class ContravariantParking[-T](things: List[T]) {
    def park(vehicle: T): Unit = {}
    def impound(vehicles: List[T]): Unit = {}
    def checkVehicles[U <: T](conditions: String): List[U] = Nil
  }


}
