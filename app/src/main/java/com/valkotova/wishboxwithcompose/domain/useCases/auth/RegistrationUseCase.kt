package com.valkotova.wishboxwithcompose.domain.useCases.auth

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.model.Token
import com.valkotova.wishboxwithcompose.domain.model.users.CredentialsInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository,
    private val userRepository: UserRepository
) {
    suspend fun execute(email: String, password: String): CredentialsInfo {
        val tokenResult = authRepository.registration(email = email, password = password)
        prefsRepository.setToken(tokenResult.token)
        val result = userRepository.me()
        result.id?.let {
            prefsRepository.setMyId(it)
        }
        return result
    }
}