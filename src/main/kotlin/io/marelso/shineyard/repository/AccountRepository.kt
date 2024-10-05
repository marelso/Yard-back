package io.marelso.shineyard.repository

import io.marelso.shineyard.domain.Account
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: MongoRepository<Account, String> {
    fun findByEmail(email: String): Account?
}