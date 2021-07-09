package catscourse.lectures.part1recap

object TCVariance extends App {

  import cats.instances.int._
  import cats.instances.option._
  import cats.syntax.eq._

  Option(2) === Option(3)
  Option(2) === Some(2)
  Option(2) === None

  class Animal
  class Cat extends Animal

  class Cage[+T]

  val cage: Cage[Animal] = new Cage[Cat]

  class Vet[-T]
  val vet: Vet[Cat] = new Vet[Animal]

  // contravariant
  trait SoundMaker[-T]
  implicit object AnimalSoundMaker extends SoundMaker[Animal]
  def makeSound[T](implicit soundMaker: SoundMaker[T]): Unit = println("wow")
  makeSound[Animal]
  makeSound[Cat]

  // covariant
  trait AnimalShow[+T] {
    def show: String
  }
  implicit object GeneralAnimalShow extends AnimalShow[Animal] {
    override def show: String = "aimals vverywhere"
  }
  implicit object CatsShow extends AnimalShow[Cat] {
    override def show: String = "so many cats"
  }
  def organizeShow[T](implicit event: AnimalShow[T]): String = event.show


}
