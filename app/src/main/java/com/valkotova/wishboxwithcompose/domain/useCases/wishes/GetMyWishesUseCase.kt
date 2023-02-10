package com.valkotova.wishboxwithcompose.domain.useCases.wishes

import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.WishRepository
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import javax.inject.Inject

class GetMyWishesUseCase @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val wishRepository: WishRepository
) {
    suspend fun execute(page : Int, name : String) : List<WishShortInfo>{
        return wishRepository.getAllMyWishes(page, name)
    }
}