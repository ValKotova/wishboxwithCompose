package com.valkotova.wishboxwithcompose.domain.useCases.wishes

import com.valkotova.wishboxwithcompose.domain.Repositories.WishRepository
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import javax.inject.Inject

class GetWishByIdUseCase @Inject constructor(
    private val wishRepository: WishRepository
) {
    suspend fun execute(id : Int) : WishInfo {
        return wishRepository.getWishById(id)
    }
}