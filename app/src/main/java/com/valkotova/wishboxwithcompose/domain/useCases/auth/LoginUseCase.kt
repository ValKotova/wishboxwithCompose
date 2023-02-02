package com.valkotova.wishboxwithcompose.domain.useCases.auth

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.model.Token
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository, private val prefsRepository: PrefsRepository) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun execute(email: String, password: String): Token {
        val result = authRepository.login(email = email, password = password)
        prefsRepository.setToken(result.token)
        return result
    }
}