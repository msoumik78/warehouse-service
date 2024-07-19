package com.example.central_monitoring_service.service

import com.example.central_monitoring_service.config.NatsConfig
import org.springframework.messaging.Message
import org.springframework.stereotype.Service

@Service
class UDPListenerTemp(
  private val natsPublisher: NATSPublisher,
  private val natsConfig: NatsConfig
) {
  fun handleMessage(message: Message<ByteArray>) {
    val data = String((message.payload))
    println("Temp data received = $data")
    natsPublisher.sendMessage(natsConfig.natsTempTopicName,data)
  }
}

@Service
class UDPListenerHumidity(
  private val natsPublisher: NATSPublisher,
  private val natsConfig: NatsConfig
) {
  fun handleMessage(message: Message<ByteArray>) {
    val data = String( message.payload)
    println("Humidity data received = $data")
    natsPublisher.sendMessage(natsConfig.natsHumidityTopicName,data)
  }
}

