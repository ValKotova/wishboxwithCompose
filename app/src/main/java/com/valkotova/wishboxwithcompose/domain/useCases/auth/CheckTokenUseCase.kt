package com.valkotova.wishboxwithcompose.domain.useCases.auth

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.model.users.CredentialsInfo
import javax.inject.Inject

class CheckTokenUseCase @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val userRepository: UserRepository
) {
    suspend fun execute() : CredentialsInfo?{
        val token = prefsRepository.getToken()
        return if(token.isNotEmpty()) {
            userRepository.me()
        } else null
    }
}