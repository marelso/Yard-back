package io.marelso.shineyard.domain

data class Sensor(
    val currentMoistureLevel: Int,
    val currentMoisturePercent: Int,
    val currentWaterVolume: Float,
    val maximumWaterVolume: Float
)