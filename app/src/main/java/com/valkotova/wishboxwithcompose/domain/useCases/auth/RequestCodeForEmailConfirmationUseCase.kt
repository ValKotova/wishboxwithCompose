package com.valkotova.wishboxwithcompose.domain.useCases.auth

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import javax.inject.Inject

class RequestCodeForEmailConfirmationUseCase @Inject constructor(private val authRepository: AuthRepository){

    suspend fun execute() {
        return authRepository.requestEmailConfirmation()
    }

}