package advancedscala.lectures.part3concurrency

import Intro.account

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

object Intro extends App {

//  val thread = new Thread ( () => {
//    println(Thread.currentThread().getId)
//    println("hello")
//  }
//  )
//
//  new Thread(() => println("I run easy!")).start()
//
//  println(Thread.currentThread().getId)
//  thread.start()
//
//  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("Hello")))
//  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println(" goodbye")))
//  threadHello.start()
//  threadGoodbye.start()
//
//  // executors
//  val pool = Executors.newFixedThreadPool(10)
//  pool.execute(() => println("some thing in the thread pool"))
//
//  /*
//  pool.shutdownNow()
//   */
//  pool.shutdown()
//  println(pool.isShutdown)

  class BankAccount(var balance:AtomicInteger) {
    override def toString: String = s"Account: $balance"
  }

  def buy(account: BankAccount, thing:String, price:Int): Unit = {
    account.balance.addAndGet(price)
  }

  def buySafe(account: BankAccount, thing:String, price:Int): Unit = {
    account.synchronized {
      account.balance.addAndGet(price)

    }
  }

  val account = new BankAccount(new AtomicInteger(1000000))
  val safeAccount = new BankAccount(new AtomicInteger(1000000))

  def buyingSpree(bankAccount: BankAccount) = {
    new Thread(() => {
      (1 to 100000).foreach(_ => buy(bankAccount, "", 1))
    })
  }

  def safeBuyingSpree(bankAccount: BankAccount) = {
    new Thread(() => {
      (1 to 100000).foreach(_ => buySafe(bankAccount, "", 1))
    })
  }

//  val thread1 = buyingSpree(account)
//  val thread2 = buyingSpree(account)
//  val thread3 = safeBuyingSpree(safeAccount)
//  val thread4 = safeBuyingSpree(safeAccount)
//  thread1.start()
//  thread2.start()
//  thread3.start()
//  thread4.start()
//  Thread.sleep(1000)
//  println(s"Account balance is ${account.balance}")
//  println(s"Safe account balance is ${safeAccount.balance}")
//

  // Inception threads

//  def inceptionThread(currentThread: Int, maxThreads: Int): Unit = {
//    if (currentThread <= maxThreads) {
//      val thread = new Thread(() => {
//        inceptionThread(currentThread + 1, maxThreads)
//      })
//      thread.start()
//      thread.join()
//      println(s"Thread number $currentThread")
//    }
//  }

  def inceptionThread(currentThread: Int, maxThreads: Int): Thread = {
    new Thread(() => {
      if (currentThread < maxThreads) {
        val nextThread = inceptionThread(currentThread + 1, maxThreads)
        nextThread.start()
        nextThread.join()
      }
      println(s"I am thread $currentThread")
    })
  }
  inceptionThread(1, 50).start()

  //
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  println(x)

  // sleep fallacy
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "scala is awesome"
  })
  message = "scala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message)





}
