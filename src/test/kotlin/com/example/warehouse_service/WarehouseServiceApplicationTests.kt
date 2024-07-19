package com.example.warehouse_service

import io.nats.client.Connection
import io.nats.client.Message
import io.nats.client.Nats
import io.nats.client.Options
import io.nats.client.Subscription
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.io.IOException
import java.lang.Thread.sleep
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.nio.charset.StandardCharsets

@SpringBootTest
class WarehouseServiceApplicationTests {

  val natsURL = "nats://localhost:4222"
  var connection : Connection?= null

    @BeforeEach
  fun initialize() {
    val options: Options = Options.builder()
      .server(natsURL)
      .build()
    connection =  Nats.connect(options)
  }

  @AfterEach
  fun shutdown() {
    connection?.close()
  }

  @Test
	fun contextLoads() {
	}

  @Test
  fun checkIfTempReadingCollected() {
    val messageTobeSent = """
      {"sensor_id" : "1", "value": "60"}
    """.trimIndent()
    sendUDPMessage(messageTobeSent)
    val (messageStr, subscription) = readMessageFromNATS()
    assert(messageStr == "60")
    subscription.unsubscribe();
  }

  @Test
  fun checkIfHumidityReadingCollected() {
    val messageTobeSent = """
      {"sensor_id" : "1", "value": "20"}
    """.trimIndent()
    sendUDPMessage(messageTobeSent, 3355)
    val (messageStr, subscription) = readMessageFromNATS("HUMIDITY")
    assert(messageStr == "20")
    subscription.unsubscribe();
  }


  fun readMessageFromNATS(topic: String = "TEMP") : Pair<String, Subscription> {
    val subscription: Subscription = connection!!.subscribe(topic)
    sleep(1000)
    val message: Message = subscription.nextMessage(10)
    val messageStr = String(message.data, StandardCharsets.UTF_8)
    return Pair(messageStr , subscription)
  }

  fun sendUDPMessage(messageTobeSent: String, port : Int = 3344) {
    val sock = InetSocketAddress("localhost", port)
    val udpMessage: ByteArray = messageTobeSent.toByteArray()
    var packet: DatagramPacket?
    try {
      DatagramSocket().use { socket ->
        packet = DatagramPacket(udpMessage, udpMessage.size, sock)
        socket.send(packet)
        socket.close()
      }
    } catch (e: IOException) {
      println("Exception encountered in sending UDP ... ${e.message}")
    }
  }


}


