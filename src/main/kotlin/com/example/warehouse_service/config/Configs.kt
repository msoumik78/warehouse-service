package com.example.warehouse_service.config

import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Options
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter


@Configuration
class NatsConfig {
    @Value("\${job.nats.uri}")
    val natsUri: String? = null

    @Value("\${job.nats.temp-topic-name}")
    val natsTempTopicName: String? = null

    @Value("\${job.nats.humidity-topic-name}")
    val natsHumidityTopicName: String? = null

    @Bean
    fun getNATSConnection(): Connection {
      val options: Options = Options.builder()
        .server(natsUri)
        .build()
      return Nats.connect(options)
    }
}


@Configuration
class UdpConfig {
      @Value("\${udp-messages.temp.port}")
      private val udpTempPort: Int = 0

      @Value("\${udp-messages.humidity.port}")
      private val udpHumidityPort: Int = 0

      @Bean
      fun processUniCastUdpMessageTemp(): IntegrationFlow {
        return IntegrationFlow
          .from(UnicastReceivingChannelAdapter(udpTempPort))
          .handle("UDPListenerForTemp", "handleMessage")
          .get()
      }
      @Bean
      fun processUniCastUdpMessageHumidity(): IntegrationFlow {
        return IntegrationFlow
          .from(UnicastReceivingChannelAdapter(udpHumidityPort))
          .handle("UDPListenerForHumidity", "handleMessage")
          .get()
      }
}

