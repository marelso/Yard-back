package io.marelso.shineyard.service

import io.marelso.shineyard.domain.Device
import io.marelso.shineyard.repository.RemoteRepository
import org.springframework.stereotype.Service

@Service
class DeviceService(private val database: RemoteRepository) {
    fun getDeviceById(id: String): Device? = database.getDeviceById(id)

    fun findAllByIds(accountDevices: List<String>): List<Device> = mutableListOf<Device>().apply {
        accountDevices.map { device ->
            getDeviceById(device)?.let {
                add(it.apply { id = device })
            }
        }
    }
}