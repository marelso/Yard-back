package io.marelso.shineyard.controller

import io.marelso.shineyard.domain.AccountCreateDto
import io.marelso.shineyard.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/accounts")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping
    fun create(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody request: AccountCreateDto
    ) = ResponseEntity.ok(accountService.save(request))
}