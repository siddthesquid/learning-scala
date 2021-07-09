package advancedscala.lectures.part5typesystem

import scala.jdk.Priority2FunctionExtensions

object SelfTypes extends App {

  trait Instrumentalist {
    def play(): Unit
  }

  trait Singer { self: Instrumentalist =>
    def sing(): Unit
  }

  class LeadSinger extends Singer with Instrumentalist {
    override def sing(): Unit = ???
    override def play(): Unit = ???
  }

  class Guitarist extends Instrumentalist {
    def play() = println("solo")
  }

  val ericClapton = new Guitarist with Singer {
    override def sing(): Unit = "yo"
  }

  class A
  class B { self: A => }

  class Component
  class ComponentA extends Component
  class ComponentB extends Component
  class DependentComponent(val component: Component)

  trait ScalaComponent {
    def action(x: Int): String
  }
  trait ScalaDependentComponent { self: ScalaComponent =>
    def dependentAction(x: Int): String = action(x) + " this rocks!"
  }
  trait ScalaApplication { self: ScalaDependentComponent => }

  // CAKE PATTERN

  // layer 1 - components
  trait Picture extends ScalaComponent
  trait Stats extends ScalaComponent

  // layer 2 - components
  trait Profile extends ScalaDependentComponent with Picture
  trait Analytics extends ScalaDependentComponent with Stats

  // layer 3 - app
  trait AnalyticsApp extends ScalaApplication with Analytics


  // cyclical dependencies
  trait X {self: Y => }
  trait Y {self: X => }

}
