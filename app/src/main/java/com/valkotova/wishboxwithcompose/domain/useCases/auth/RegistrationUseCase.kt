package com.valkotova.wishboxwithcompose.domain.useCases.auth

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository
) {
    suspend fun execute(email: String, password: String): Token {
        val result = authRepository.registration(email = email, password = password)
        prefsRepository.setToken(result.token)
        return result

    }
}