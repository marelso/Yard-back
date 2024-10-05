package io.marelso.shineyard.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.marelso.shineyard.config.TokenProperties
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val properties: TokenProperties
) {
    private val secret = Keys.hmacShaKeyFor(properties.key.toByteArray())

    fun generate(account: UserDetails, expirationDate: Date, additionalClaims: Map<String, Any> = emptyMap()): String {
        return Jwts.builder().claims().subject(account.username).issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate).add(additionalClaims).and().signWith(secret).compact()
    }

    fun extractEmail(token: String): String = getAllClaims(token).subject

    fun isExpired(token: String): Boolean = getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))

    fun isValid(token: String, account: UserDetails): Boolean {
        return account.username == extractEmail(token) && isExpired(token).not()
    }

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser().verifyWith(secret).build()
        return parser.parseSignedClaims(token).payload
    }
}