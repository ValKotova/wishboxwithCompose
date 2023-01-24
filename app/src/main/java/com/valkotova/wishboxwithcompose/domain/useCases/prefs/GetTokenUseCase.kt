package com.valkotova.wishboxwithcompose.domain.useCases.prefs

import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val prefsRepository: PrefsRepository) {
    fun execute(): String{
        return prefsRepository.getToken()
    }
}