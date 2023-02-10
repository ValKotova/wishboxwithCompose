package com.valkotova.wishboxwithcompose.domain.Repositories

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishStatus
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo

interface WishRepository {

    suspend fun createWish(
        name: String,
        link: String?,
        price: String?,
        comment: String?,
        previews: List<Int>
    ): Int?

    suspend fun removeMyWish(id: Int)

    suspend fun updateWish(
        id: Int,
        name: String? = null,
        link: String? = null,
        price: String? = null,
        comment: String? = null,
        favorite: Boolean? = null,
        previews: List<Int>? = null
    )

    suspend fun setWishStatus(
        wishId: Int,
        status: WishStatus
    )

    suspend fun dropWishStatus(
        wishId: Int
    )

    suspend fun getAllMyWishes(
        page: Int,
        name: String): List<WishShortInfo>

    suspend fun getWishById(id: Int): WishInfo

    suspend fun removeFromFavorites(id: Int)

    suspend fun addToFavorites(id: Int)
}