package io.marelso.shineyard.controller

import io.marelso.shineyard.domain.AuthenticationRequestDto
import io.marelso.shineyard.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping
    fun auth(@RequestBody request: AuthenticationRequestDto) = ResponseEntity.ok<String>(authenticationService.authenticate(request))
}