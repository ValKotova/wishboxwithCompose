package com.valkotova.wishboxwithcompose.domain.Repositories

import com.valkotova.wishboxwithcompose.data.model.responses.calendar.AllListsResponseForCalendar
import com.valkotova.wishboxwithcompose.domain.model.wishlists.DataForCreateWishListRequest
import com.valkotova.wishboxwithcompose.domain.model.wishlists.WishListInfo

interface WishListRepository {

    suspend fun createWishList(data: DataForCreateWishListRequest)

    suspend fun updateWishList(
        id: Int,
        name: String? = null,
        wishes: List<Int>? = null,
        viewers: List<Int>? = null,
        visibility: Int? = null,
        date: Long? = null,
        preview: Int? = null,
        favorite: Boolean? = null,
    )

    suspend fun updateWishList(
        id: Int,
        favorite: Boolean,
        data: DataForCreateWishListRequest
    )

    suspend fun addWishToList(
        wishList: Int,
        wishes: List<Int>
    )

    suspend fun getWishListById(wishListId: Int?,
                                ownerUserId: Int?,
                                currentUserId : Int,
                                isMine : Boolean,
                                defaultWishListName: String?): WishListInfo

    suspend fun getWishListLinkById(wishListId: Int): String

    suspend fun getAllWishListsByUser(userId: Int,
                                      page: Int,
                                      currentUserId : Int,
                                      isMine : Boolean,
                                      defaultWishListName: String?): List<WishListInfo>

    suspend fun getAllWishListsByMonth(month: Int, year: Int): AllListsResponseForCalendar

    suspend fun removeMyWishList(wishListId: Int)
}