package io.marelso.shineyard.repository

import io.marelso.shineyard.domain.Device
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "remoteRepository", url = "https://healthier-19122-default-rtdb.firebaseio.com")
interface RemoteRepository {
    @GetMapping("/ESP32_Devices/{deviceId}.json")
    fun getDeviceById(@PathVariable deviceId: String): Device

    @GetMapping("/ESP32_Devices.json")
    fun getAllDevices(): Map<String, Device>
}