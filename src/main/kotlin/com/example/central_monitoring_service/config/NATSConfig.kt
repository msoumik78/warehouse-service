package com.example.central_monitoring_service.config

import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Options
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class NATSConfig {
  @Value("\${job.nats.uri}")
  private val natsUri: String? = null

  @Bean
  fun getNATSConnection(): Connection {
    val options: Options = Options.builder()
      .server(natsUri)
      .build()
    return Nats.connect(options)
  }
}

