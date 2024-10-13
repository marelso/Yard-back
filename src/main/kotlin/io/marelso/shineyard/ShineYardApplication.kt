package io.marelso.shineyard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ShineYardApplication

fun main(args: Array<String>) {
	runApplication<ShineYardApplication>(*args)
}
