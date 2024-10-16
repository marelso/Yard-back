package io.marelso.shineyard.domain


data class Device(
    var id: String? = null,
    val name: String,
    val sensors: Sensor
)