//#full-example
package com.example


import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.OrderProcessor.Order
import com.example.Shipper.Shipment

object Product extends Enumeration {
  val Jacket, Sneakers, Umbrella, Socks = Value
}

object OrderProcessor {

  final case class Order(id: Int, produkt: Product.Value, number: Int)

  def apply(): Behavior[Order] = Behaviors.setup { context =>
    var shipper = context.spawn(Shipper(), "shipper")

    Behaviors.receiveMessage { message =>
      context.log.info(message.toString())

      shipper ! Shipper.Shipment(message.id, message.produkt, message.number)

      Behaviors.same
    }
  }

}

object Shipper {

  final case class Shipment(orderid: Int, produkt: Product.Value, number: Int)

  def apply(): Behavior[Shipper.Shipment] = Behaviors.receive {
    (context, message) =>
    context.log.info("Shipping " + message.toString())
    Behaviors.same
  }
}

//#main-class
object AkkaQuickstart extends App {
  val orderProcessor: ActorSystem[OrderProcessor.Order] = ActorSystem(OrderProcessor(), "Orders")
  orderProcessor ! Order(0, Product.Jacket, 2)
  orderProcessor ! Order(1, Product.Sneakers, 1)
  orderProcessor ! Order(2, Product.Socks, 5)
  orderProcessor ! Order(3, Product.Umbrella, 3)
}
//#main-class
//#full-example
