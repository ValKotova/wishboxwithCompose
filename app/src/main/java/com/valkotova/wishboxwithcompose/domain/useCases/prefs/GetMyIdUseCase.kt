package com.valkotova.wishboxwithcompose.domain.useCases.prefs

import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import javax.inject.Inject

class GetMyIdUseCase @Inject constructor(private val prefsRepository: PrefsRepository) {
    fun execute(): Int{
        return prefsRepository.getMyId()
    }
}