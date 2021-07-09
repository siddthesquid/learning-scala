package advancedscala.playground

object ConcurrentPlayground extends App {

  case class UselessContainer(var a: Int = 0) {
    def printHello(i: Int) = {
      a += 1
      (1 to 10).foreach { _ =>
        Thread.sleep(10)
        println(s"hello ${i}")
      }
    }
    def printBye(i: Int) = println(s"bye ${i}")
  }
  val container = UselessContainer(10)

  val threads = (1 to 1000).map { i =>
    new Thread(() => {
      container.synchronized{
        (1 to 100).foreach { j =>
          container.printHello(j * 1000 + i)
          container.printBye(j * 1000 + i)
        }
      }
    })
  }

//  val threads = (1 to 100).map { i =>
//    new Thread(() => {
//      container.synchronized {
//        println(s"Hello from $i")
//        Thread.sleep(50)
//        container.printHello(i)
//        container.wait()
//        println(s"Bye from $i")
//        Thread.sleep(100)
//        container.printBye(i)
//      }
//    })
//  }
  threads.foreach(_.start())
  threads.foreach(_.join())
  println(container.a)
//  threads.foreach(_ => {
//    container.synchronized {
//      container.notify()
//    }
//  })

}
