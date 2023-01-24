package com.valkotova.wishboxwithcompose.domain.useCases.prefs

import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import javax.inject.Inject

class SetMyIdUseCase @Inject constructor(private val prefsRepository: PrefsRepository) {
    fun execute(id: Int) {
        prefsRepository.setMyId(id = id)
    }
}