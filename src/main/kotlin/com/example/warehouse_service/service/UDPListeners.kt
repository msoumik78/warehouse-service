package com.example.warehouse_service.service

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.example.warehouse_service.config.NatsConfig
import org.springframework.messaging.Message
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UDPListenerForTemp(
  private val natsPublisher: NATSPublisher,
  private val natsConfig: NatsConfig
) {
  var logger: Logger = Logger.getLogger(UDPListenerForTemp::class.java.getName())

  fun handleMessage(message: Message<ByteArray>) {
    val data = String((message.payload))
    val tempValue = Klaxon().parse<SensorPayload>(data)?.sensorValue
    logger.info("Temp data received = $tempValue")
    natsPublisher.sendMessage(natsConfig.natsTempTopicName,tempValue!!)
  }
}

@Service
class UDPListenerForHumidity(
  private val natsPublisher: NATSPublisher,
  private val natsConfig: NatsConfig
) {
  var logger: Logger = Logger.getLogger(UDPListenerForHumidity::class.java.getName())

  fun handleMessage(message: Message<ByteArray>) {
    val data = String( message.payload)
    val humidValue = Klaxon().parse<SensorPayload>(data)?.sensorValue
    logger.info("Humidity data received = $humidValue")
    natsPublisher.sendMessage(natsConfig.natsHumidityTopicName,humidValue!!)
  }
}

data class SensorPayload(
  @Json(name = "sensor_id")
  val sensorId: String,
  @Json(name = "value")
  val sensorValue: String,
)

