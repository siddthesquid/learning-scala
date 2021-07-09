package catscourse.playground

import cats.Eval

object PlayGround {

  val meaningOfLife = Eval.later {
    println("learning cats; computing abs fjdsalkf")
    42
  }

  def main(args: Array[String]): Unit = {
    println(meaningOfLife.value)
  }

}
