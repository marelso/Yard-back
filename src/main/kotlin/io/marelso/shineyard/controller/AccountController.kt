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
    @PostMapping("/user")
    fun create(
        @RequestBody request: AccountCreateDto
    ) = ResponseEntity.ok(accountService.save(request))

    @PatchMapping("/{accountId}/device")
    fun pushDevice(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable("accountId") id: String,
        @RequestParam("device") device: String
    ) = ResponseEntity.ok(accountService.pushDevice(id, device))

    @PatchMapping("/{accountId}/token")
    fun pushToken(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable("accountId") accountId: String,
        @RequestParam("token") token: String
    ) = ResponseEntity.ok(accountService.pushToken(accountId = accountId, token = token))

    @GetMapping("/{id}")
    fun get(
        @RequestHeader("Authorization") bearerToken: String,
        @PathVariable("id") id: String
    ) = ResponseEntity.ok(accountService.get(id))
}