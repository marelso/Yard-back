package io.marelso.shineyard.service

import io.marelso.shineyard.config.TokenProperties
import io.marelso.shineyard.domain.dto.AccountDto
import io.marelso.shineyard.domain.factory.AccountFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val accountFactory: AccountFactory,
    private val accountSecurityService: AccountSecurityService,
    private val tokenService: TokenService,
    private val tokenProperties: TokenProperties,
    private val deviceService: DeviceService
) {
    fun authenticate(email: String, password: String): AccountDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                email,
                password
            )
        )

        val account = accountSecurityService.loadUserByUsername(email)
        val token = tokenService.generate(
            account = account,
            expirationDate = Date(System.currentTimeMillis() + tokenProperties.accessTokenExpiration),
        )
        val devices = deviceService.findAllByIds(accountDevices = account.accountDevices)

        return accountFactory.from(account, devices, token)
    }
}