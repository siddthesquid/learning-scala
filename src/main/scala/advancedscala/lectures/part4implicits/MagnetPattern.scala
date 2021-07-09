package advancedscala.lectures.part4implicits

import scala.concurrent.Future

object MagnetPattern extends App {

  class P2PRequest
  class P2PResponse
  class Serializer[T]

  trait Actor {
    def receive(statusCode: Int): Int
    def receive(request: P2PRequest): Int
    def receive(response: P2PResponse): Int
    def receive[T : Serializer](message: T): Int
    def receive[T : Serializer](message: T, statusCode: Int): Int
    def receive(future: Future[P2PRequest]): Int
  }

  trait MessageMagnet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageMagnet[R]): R = magnet()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageMagnet[Int] {
    def apply(): Int = {
      println("handling p2p request")
      42
    }
  }

  /*
  implicit class FromP2PResponse(request: P2PResponse) extends MessageMagnet[Int] {
    def apply(): Int = {
      println("handling p2p response")
      24
    }
  }*/
  implicit class FromP2PResponseString(request: P2PResponse) extends MessageMagnet[String] {
    def apply(): String = {
      println("handling p2p response")
      "hello"
    }
  }


  receive(new P2PResponse)
  receive(new P2PRequest)

}
