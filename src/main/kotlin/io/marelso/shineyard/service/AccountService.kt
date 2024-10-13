package io.marelso.shineyard.service

import io.marelso.shineyard.domain.Account
import io.marelso.shineyard.domain.AccountCreateDto
import io.marelso.shineyard.domain.AccountDto
import io.marelso.shineyard.repository.AccountRepository
import lombok.RequiredArgsConstructor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AccountService(
    private val accountRepository: AccountRepository,
    private val deviceService: DeviceService,
    private val passwordEncoder: PasswordEncoder
) {
    fun save(request: AccountCreateDto): Account {
        request.takeIf {
            accountRepository.findByEmail(request.email) == null
        }?.let {
            return accountRepository.save(
                Account(
                    firstName = request.firstName,
                    lastName = request.lastName,
                    pictureUrl = request.pictureUrl,
                    email = request.email,
                    password = passwordEncoder.encode(request.password),
                    accountDevices = listOf(),
                    accountTokens = listOf()
                )
            )
        } ?: throw RuntimeException("Username already created")
    }

    fun get(id: String): AccountDto {
        accountRepository.findByIdOrNull(id)?.let {
            return AccountDto(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                pictureUrl = it.pictureUrl,
                email = it.email,
                accountDevices = deviceService.findAllByIds(it.accountDevices)
            )
        } ?: throw RuntimeException("Account with id $id not found")
    }

    fun pushDevice(accountId: String, deviceId: String): AccountDto {
        accountRepository.findByIdOrNull(accountId)?.let { givenAccount ->
            if(givenAccount.accountDevices.contains(deviceId)) throw RuntimeException("User already have access to this device")

            deviceService.getDeviceById(deviceId)?.let {
                val account = accountRepository.save(
                    givenAccount.copy(
                        accountDevices = mutableListOf<String>().apply {
                            addAll(givenAccount.accountDevices)
                            add(deviceId)
                        }
                    )
                )

                return AccountDto(
                    id = account.id,
                    firstName = account.firstName,
                    lastName = account.lastName,
                    pictureUrl = account.pictureUrl,
                    email = account.email,
                    accountDevices = account.accountDevices.map {
                        deviceService.getDeviceById(it) ?: throw RuntimeException("Device with id $it not found")
                    }
                )
            } ?: throw RuntimeException("Device with id $accountId not found")
        } ?: throw RuntimeException("Account with id $accountId not found")
    }

    fun pushToken(accountId: String, token: String): AccountDto {
        accountRepository.findByIdOrNull(accountId)?.let { givenAccount ->
            if(givenAccount.accountTokens.contains(token)) throw RuntimeException("User already have this token")
            val account = accountRepository.save(
                givenAccount.copy(
                    accountTokens = mutableListOf<String>().apply {
                        addAll(givenAccount.accountTokens)
                        add(token)
                    }
                )
            )

            return AccountDto(
                id = account.id,
                firstName = account.firstName,
                lastName = account.lastName,
                pictureUrl = account.pictureUrl,
                email = account.email,
                accountDevices = account.accountDevices.map {
                    deviceService.getDeviceById(it) ?: throw RuntimeException("Device with id $it not found")
                }
            )
        } ?: throw RuntimeException("Account with id $accountId not found")
    }
}