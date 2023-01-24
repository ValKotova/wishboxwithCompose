package com.valkotova.wishboxwithcompose.domain.useCases.prefs

import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val prefsRepository: PrefsRepository) {
    fun execute(){
        prefsRepository.logout()
    }
}