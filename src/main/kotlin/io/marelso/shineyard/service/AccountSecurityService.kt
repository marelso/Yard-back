package io.marelso.shineyard.service

import io.marelso.shineyard.domain.Account
import io.marelso.shineyard.repository.AccountRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias SecurityUser = Account

@Service
class AccountSecurityService(private val accountRepository: AccountRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository.findByEmail(username)?.mapToUserDetails() ?: throw UsernameNotFoundException(username)
    }

    private fun SecurityUser.mapToUserDetails(): UserDetails {
        return User.builder()
            .username(email)
            .password(password)
            .build()
    }
}