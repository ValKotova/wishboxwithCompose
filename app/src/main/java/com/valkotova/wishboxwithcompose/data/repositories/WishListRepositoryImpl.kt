package com.valkotova.wishboxwithcompose.data.repositories

import com.valkotova.wishboxwithcompose.data.model.requests.AddWishToListRequest
import com.valkotova.wishboxwithcompose.data.model.requests.CreateWishListRequest
import com.valkotova.wishboxwithcompose.data.model.requests.UpdateWishListRequest
import com.valkotova.wishboxwithcompose.data.model.responses.calendar.AllListsResponseForCalendar
import com.valkotova.wishboxwithcompose.data.network.WishlistAPI
import com.valkotova.wishboxwithcompose.data.utils.BaseBackendApi
import com.valkotova.wishboxwithcompose.domain.Repositories.WishListRepository
import com.valkotova.wishboxwithcompose.domain.model.wishlists.DataForCreateWishListRequest
import com.valkotova.wishboxwithcompose.domain.model.wishlists.WishListInfo
import com.valkotova.wishboxwithcompose.domain.model.wishlists.to_WishListInfo
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WishListRepositoryImpl@Inject constructor(private val wishlistAPI: WishlistAPI,
                                                json: Json
) : WishListRepository, BaseBackendApi(json) {
    override suspend fun createWishList(data: DataForCreateWishListRequest) {
        return safeApiCallUnit {
            wishlistAPI.createWishList(
                CreateWishListRequest(
                    data.name,
                    data.wishes.map { it.id },
                    if (data.visibility == 2) data.viewers.map { it.id } else listOf(),
                    data.visibility,
                    data.date,
                    data.preview?.id
                )
            )
        }
    }

    override suspend fun updateWishList(
        id: Int,
        name: String?,
        wishes: List<Int>?,
        viewers: List<Int>?,
        visibility: Int?,
        date: Long?,
        preview: Int?,
        favorite: Boolean?
    ) {
        return safeApiCallUnit {
            wishlistAPI.updateWishList(
                UpdateWishListRequest(
                    id = id,
                    name = name,
                    wishes = wishes,
                    viewers = viewers,
                    visibility = visibility,
                    date = date,
                    preview = preview,
                    favorite = favorite
                )
            )
        }
    }

    override suspend fun updateWishList(
        id: Int,
        favorite: Boolean,
        data: DataForCreateWishListRequest
    ) {
        return safeApiCallUnit {
            wishlistAPI.updateWishList(
                UpdateWishListRequest(
                    id = id,
                    name = data.name,
                    wishes = data.wishes.map { it.id },
                    viewers = data.viewers.map{ it.id },
                    visibility = data.visibility,
                    date = data.date,
                    preview = data.preview?.id,
                    favorite = favorite
                )
            )
        }
    }

    override suspend fun addWishToList(wishList: Int, wishes: List<Int>) {
        return safeApiCallUnit {
            wishlistAPI.addWishToList(
                AddWishToListRequest(wishList, wishes)
            )
        }
    }

    override suspend fun getWishListById(
        wishListId: Int?,
        ownerUserId: Int?,
        currentUserId : Int,
        isMine : Boolean,
        defaultWishListName: String?
    ): WishListInfo {
        return safeApiCall { wishlistAPI.getWishListById(wishListId, ownerUserId) }
            .to_WishListInfo(currentUserId, isMine, defaultWishListName)
    }

    override suspend fun getWishListLinkById(wishListId: Int): String {
        return safeApiCall { wishlistAPI.getWishListLinkById(wishListId) }.token
    }

    override suspend fun getAllWishListsByUser(
        userId: Int,
        page: Int,
        currentUserId : Int,
        isMine : Boolean,
        defaultWishListName: String?
    ): List<WishListInfo> {
        return safeApiCall { wishlistAPI.getAllWishListsByUser(userId, page, 1) }
            .lists.map {
            it.to_WishListInfo(currentUserId, isMine, defaultWishListName)
        }
    }

    override suspend fun getAllWishListsByMonth(
        month: Int,
        year: Int
    ): AllListsResponseForCalendar {
        return safeApiCall { wishlistAPI.getAllWishListsByMonth(month, year) }.to_AllResponseForCalendar()
    }

    override suspend fun removeMyWishList(wishListId: Int) {
        return safeApiCallUnit { wishlistAPI.removeMyWishList(wishListId) }
    }
}