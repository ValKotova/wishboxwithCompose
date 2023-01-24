package com.valkotova.wishboxwithcompose.domain.useCases.prefs

import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(private val prefsRepository: PrefsRepository) {
    fun execute(token: String){
        prefsRepository.setToken(token = token)
    }
}