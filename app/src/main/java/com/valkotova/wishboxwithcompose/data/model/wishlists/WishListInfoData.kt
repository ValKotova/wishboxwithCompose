package com.valkotova.wishboxwithcompose.data.model.wishlists

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishShortInfoData
import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData
import com.valkotova.wishboxwithcompose.data.model.users.UserInfoData
import kotlinx.serialization.Serializable

@Serializable
data class WishListInfoData(
    val id: Int?,
    val name: String,
    val visibility: Int,
    val user: Int,
    val preview: FileInfoData?,
    val date: Long?,
    val isFavorite: Boolean,
    val viewers: List<UserInfoData>?,
    val wishes: List<WishShortInfoData>?
)