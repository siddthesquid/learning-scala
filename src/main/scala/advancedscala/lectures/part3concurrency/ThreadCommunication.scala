package advancedscala.lectures.part3concurrency

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {

  class SimpleContainer {
    private var value: Int = 0
    def isEmpty: Boolean = value == 0
    def set(newValue: Int): Unit = value = newValue
    def get: Int = {
      val result = value
      value = 0
      result
    }
  }

  def naiveProdCons(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      while(container.isEmpty) {
        println("[consumer] actively waiting...")
      }
      println(s"[consumer] I have consumed ${container.get}")
    })

    val producer = new Thread(() => {
      println("[producer] computing...")
      Thread.sleep(500)
      val value = 42
      println(s"[produced] produced $value")
      container.set(value)
    })

    consumer.start()
    producer.start()
  }

//  naiveProdCons()

  def smartProdCons(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      container.synchronized {
        container.wait()
      }
      println(s"[consumer] I have consumed ${container.get}")
    })
    val producer = new Thread(() => {
      println("[producer] hard at work")
      Thread.sleep(2000)
      val value = 42
      container.synchronized {
        println(s"[producer] I'm producing $value")
        container.set(value)
        container.notify()
      }
    })
    consumer.start()
    producer.start()
  }


  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      while(true){
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer ${id}] buffer empty, waiting...")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println(s"[consumer ${id}] I consumed ${x}")
          buffer.notify()
          println(s"Buffer size: ${buffer.size}")
        }
        Thread.sleep(random.nextInt(5000))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      var i = 0
      while (true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer ${id}] buffer full, waiting...")
            buffer.wait()
          }
          println(s"[producer ${id}] producing ${i}...")
          buffer.enqueue(i)
          buffer.notify()
          i += 1
          println(s"Buffer size: ${buffer.size}")
        }
        Thread.sleep(random.nextInt(5))
      }
    }
  }

  def prodConsLargeBuffer(numConsumers: Int, numProducers: Int): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumers = (1 to numConsumers).map(new Consumer(_, buffer))
    val producers = (1 to numProducers).map(new Producer(_, buffer, capacity))

    consumers.foreach(_.start())
    producers.foreach(_.start())
  }

  prodConsLargeBuffer(100, 1)


}
