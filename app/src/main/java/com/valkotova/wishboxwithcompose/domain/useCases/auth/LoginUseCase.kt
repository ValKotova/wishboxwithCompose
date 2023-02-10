package com.valkotova.wishboxwithcompose.domain.useCases.auth

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.model.Token
import com.valkotova.wishboxwithcompose.domain.model.users.CredentialsInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository,
    private val userRepository : UserRepository
) {
    suspend fun execute(email: String, password: String): CredentialsInfo {
        val token = authRepository.login(email = email, password = password)
        prefsRepository.setToken(token.token)
        val result = userRepository.me()
        result.id?.let {
            prefsRepository.setMyId(it)
        }
        return result
    }
}