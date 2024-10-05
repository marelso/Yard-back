package io.marelso.shineyard.domain

data class AuthenticationRequestDto(
    val email: String,
    val password: String
)
