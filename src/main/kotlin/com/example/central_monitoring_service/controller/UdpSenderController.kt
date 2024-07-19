package com.example.central_monitoring_service.controller

import com.example.central_monitoring_service.service.NATSPublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress


@RestController
class UdpSenderController(private val natsPublisher: NATSPublisher)
{

  @PostMapping("/udp-packets")
  fun tempDataSender(@RequestBody udpMessage: UDPMessage) {
    val sock = InetSocketAddress(udpMessage.toHost, udpMessage.toPort.toInt())

    val udpMessage: ByteArray = udpMessage.payLoad.toByteArray()
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

data class UDPMessage(
  val toHost: String,
  val toPort: String,
  val payLoad: String
)
