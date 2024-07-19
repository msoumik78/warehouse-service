package com.example.central_monitoring_service.controller

import com.example.central_monitoring_service.service.NATSPublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(private val natsPublisher: NATSPublisher)
{
  @Value("\${job.nats.topic}")
  var topic: String? = null

  @GetMapping("/")
  fun index(@RequestParam("name") name: String) {
    natsPublisher.sendMessage(topic, "my name is $name")
    "Hello, $name!"
  }
}
