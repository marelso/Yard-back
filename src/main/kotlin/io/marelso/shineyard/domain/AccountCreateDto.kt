package io.marelso.shineyard.domain

data class AccountCreateDto(
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val email: String,
    val password: String
)
