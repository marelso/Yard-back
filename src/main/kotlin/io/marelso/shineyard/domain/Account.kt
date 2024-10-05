package io.marelso.shineyard.domain

data class Account(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val email: String,
    val password: String,
    val accountDevices: List<Device>
)
