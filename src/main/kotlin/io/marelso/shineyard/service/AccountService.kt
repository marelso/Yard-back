package io.marelso.shineyard.service

import io.marelso.shineyard.domain.Account
import io.marelso.shineyard.domain.AccountCreateDto
import io.marelso.shineyard.repository.AccountRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun save(request: AccountCreateDto): Account {
        return accountRepository.save(
            Account(
                firstName = request.firstName,
                lastName = request.lastName,
                pictureUrl = request.pictureUrl,
                email = request.email,
                password = passwordEncoder.encode(request.password),
                accountDevices = listOf()
            )
        )
    }
}