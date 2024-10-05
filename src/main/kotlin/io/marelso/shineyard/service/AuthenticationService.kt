package io.marelso.shineyard.service

import io.marelso.shineyard.config.TokenProperties
import io.marelso.shineyard.domain.AuthenticationRequestDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val accountSecurityService: AccountSecurityService,
    private val tokenService: TokenService,
    private val tokenProperties: TokenProperties
) {
    fun authenticate(request: AuthenticationRequestDto): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        return tokenService.generate(
            account = accountSecurityService.loadUserByUsername(request.email),
            expirationDate = Date(System.currentTimeMillis() + tokenProperties.accessTokenExpiration),
        )
    }
}