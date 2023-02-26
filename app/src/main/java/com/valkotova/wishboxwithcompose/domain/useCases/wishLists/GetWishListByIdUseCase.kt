package com.valkotova.wishboxwithcompose.domain.useCases.wishLists

import android.content.Context
import com.valkotova.wishboxwithcompose.R
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.WishListRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.WishRepository
import com.valkotova.wishboxwithcompose.domain.model.wishlists.WishListInfo
import javax.inject.Inject

class GetWishListByIdUseCase @Inject constructor(
    private val wishListRepository: WishListRepository,
    private val prefsRepository: PrefsRepository
) {
    suspend fun execute(wishListId : Int?, ownerUserId : Int?, isMine : Boolean, context : Context) : WishListInfo{
        return wishListRepository.getWishListById(
            wishListId,
            ownerUserId,
            prefsRepository.getMyId(),
            isMine,
            context.getString(R.string.my_wish_lists)
        )
    }
}