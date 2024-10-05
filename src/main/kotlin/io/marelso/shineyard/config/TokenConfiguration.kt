package io.marelso.shineyard.config

import io.marelso.shineyard.repository.AccountRepository
import io.marelso.shineyard.service.AccountSecurityService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableConfigurationProperties(TokenProperties::class)
class TokenConfiguration {
    @Bean
    fun accountSecurityService(accountRepository: AccountRepository) = AccountSecurityService(accountRepository)

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(accountRepository: AccountRepository) = DaoAuthenticationProvider().also {
        it.setUserDetailsService(accountSecurityService(accountRepository))
        it.setPasswordEncoder(encoder())
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager
}