package advancedscala.lectures.part3concurrency

object ThreadCommunicationExercises extends App {

  def testNotifyAll(): Unit = {
    val bell = new Object
    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized {
        println(s"[thread ${i}] waiting")
        bell.wait()
        println(s"[thread ${i}] hooray")
      }
    }).start())

    new Thread(() => {
      Thread.sleep(1000)
      bell.synchronized {
        bell.notifyAll()
      }
    }).start()
  }

//  testNotifyAll()

  case class Friend(name: String) {
    override def toString: String =  name
    def bow(other: Friend) = {
      this.synchronized {
        println(s"$this: I am bowing to $other")
        other.rise(this)
        println(s"$this: $other has risen")
      }
    }

    def rise(other: Friend) = {
      this.synchronized {
        println(s"$this: I am rising to $other")
      }
    }
  }

}
