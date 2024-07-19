package com.example.central_monitoring_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CentralMonitoringServiceApplication

fun main(args: Array<String>) {
	runApplication<CentralMonitoringServiceApplication>(*args)
}
