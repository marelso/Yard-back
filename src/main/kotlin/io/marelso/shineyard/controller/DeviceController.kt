package io.marelso.shineyard.controller

import io.marelso.shineyard.service.DeviceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/devices")
class DeviceController(private val deviceService: DeviceService) {
    @GetMapping
    fun getDeviceByName(@RequestParam("query") device: String) = ResponseEntity.ok(deviceService.getDeviceByName(device))
}