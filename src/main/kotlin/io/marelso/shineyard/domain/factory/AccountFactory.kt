package io.marelso.shineyard.domain.factory

import io.marelso.shineyard.domain.Account
import io.marelso.shineyard.domain.Device
import io.marelso.shineyard.domain.dto.AccountDto
import org.springframework.stereotype.Component

@Component
class AccountFactory {
    fun from(account: Account, devices: List<Device>, jwt: String): AccountDto {
        return AccountDto(
            id = account.id,
            firstName = account.firstName,
            lastName = account.lastName,
            pictureUrl = account.pictureUrl,
            email = account.email,
            jwt = jwt,
            accountDevices = devices,
        )
    }
}