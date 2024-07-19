package com.example.central_monitoring_service.service

import io.nats.client.Connection
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets


@Service
class NATSPublisher
  (private val connection: Connection)
{
  fun sendMessage(topic: String?, message: String) {
    connection.publish(topic, message.toByteArray(StandardCharsets.UTF_8))
  }
}
