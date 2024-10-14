package io.marelso.shineyard.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class Account(
    val id: String? = null,
    val firstName: String,
    val lastName: String,
    val pictureUrl: String,
    val email: String,
    @JvmField val password: String,
    val accountDevices: List<String>,
    val accountTokens: List<String>
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableSetOf()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.email
    }
}
