package io.marelso.shineyard.domain.dto

import io.marelso.shineyard.domain.Device

data class AccountDto(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val email: String,
    val jwt: String? = null,
    val accountDevices: List<Device>
)
