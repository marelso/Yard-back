package io.marelso.shineyard.service

import io.marelso.shineyard.domain.Account
import io.marelso.shineyard.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountSecurityService(private val accountRepository: AccountRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): Account {
        return accountRepository.findByEmail(username) ?: throw UsernameNotFoundException(username)
    }
}