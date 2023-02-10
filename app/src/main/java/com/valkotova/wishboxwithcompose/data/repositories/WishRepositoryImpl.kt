package com.valkotova.wishboxwithcompose.data.repositories

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishStatus
import com.valkotova.wishboxwithcompose.data.model.requests.CreateWishRequest
import com.valkotova.wishboxwithcompose.data.model.requests.DropWishStatusRequest
import com.valkotova.wishboxwithcompose.data.model.requests.UpdateWishRequest
import com.valkotova.wishboxwithcompose.data.model.requests.UpdateWishStatusRequest
import com.valkotova.wishboxwithcompose.data.network.UserAPI
import com.valkotova.wishboxwithcompose.data.network.WishAPI
import com.valkotova.wishboxwithcompose.data.utils.BaseBackendApi
import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.Repositories.WishRepository
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.to_WishInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.to_WishShortInfo
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WishRepositoryImpl@Inject constructor(private val wishAPI: WishAPI,
                                            json: Json
)
    : WishRepository, BaseBackendApi(json) {
    override suspend fun createWish(
        name: String,
        link: String?,
        price: String?,
        comment: String?,
        previews: List<Int>
    ): Int {
        return safeApiCall { wishAPI.createWish(CreateWishRequest(
            name = name, link = link, price = price, comment = comment, previews = previews
        )) }.wishId
    }

    override suspend fun removeMyWish(id: Int) {
        return safeApiCallUnit { wishAPI.removeMyWish(id) }
    }

    override suspend fun updateWish(
        id: Int,
        name: String?,
        link: String?,
        price: String?,
        comment: String?,
        favorite: Boolean?,
        previews: List<Int>?
    ) {
        safeApiCallUnit { wishAPI.updateWish(UpdateWishRequest(
            id = id,
            name = name,
            link = link,
            price = price,
            comment = comment,
            favorite = favorite,
            previews = previews
        )) }
    }

    override suspend fun setWishStatus(wishId: Int, status: WishStatus) {
        safeApiCallUnit {
            wishAPI.setWishStatus(UpdateWishStatusRequest(
                status = status, wishId = wishId
            ))
        }
    }

    override suspend fun dropWishStatus(wishId: Int) {
        safeApiCallUnit {
            wishAPI.dropWishStatus(DropWishStatusRequest(
                wishId = wishId
            ))
        }
    }

    override suspend fun getAllMyWishes(page: Int, name: String): List<WishShortInfo> {
        return safeApiCall {
            wishAPI.getAllMyWishes(name, page)
        }.wishes.map { it.to_WishShortInfo(0, false) }
    }

    override suspend fun getWishById(id: Int): WishInfo {
        return safeApiCall {
            wishAPI.getWishById(id)
        }.to_WishInfo()
    }

    override suspend fun removeFromFavorites(id: Int) {
        safeApiCallUnit {
            wishAPI.removeFromFavorites(id)
        }
    }

    override suspend fun addToFavorites(id: Int) {
        safeApiCallUnit {
            wishAPI.addToFavorites(id)
        }
    }
}