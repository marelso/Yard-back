package io.marelso.shineyard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShineYardApplication

fun main(args: Array<String>) {
	runApplication<ShineYardApplication>(*args)
}
