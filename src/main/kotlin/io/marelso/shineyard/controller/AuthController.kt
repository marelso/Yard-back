package io.marelso.shineyard.controller

import io.marelso.shineyard.domain.AccountDto
import io.marelso.shineyard.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping
    fun auth(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ) = ResponseEntity.ok<AccountDto>(authenticationService.authenticate(email, password))
}