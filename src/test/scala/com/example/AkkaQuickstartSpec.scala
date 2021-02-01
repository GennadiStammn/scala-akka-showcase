//#full-example
package com.example

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import org.scalatest.wordspec.AnyWordSpecLike
import com.example.OrderProcessor.Order
import com.example.Shipper.Shipment
import com.example.OrderProcessor
import akka.testkit.TestActorRef
import akka.actor.Props
import akka.actor.typed.scaladsl.Behaviors
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.must.Matchers
import akka.actor.testkit.typed.javadsl.ActorTestKit
import com.example.Notifier.Notification

//#definition
class AkkaQuickstartSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
//#definition

  "A Shipment" must {
    "notify the shipment" in {
      val notificationProbe = createTestProbe[Notification]()
      val underTest = spawn(Shipper());

      underTest ! Shipment(0, Product.Jacket, 1, notificationProbe.ref)

      notificationProbe.expectMessage(Notification(0, true))
    }
  }

}
//#full-example
