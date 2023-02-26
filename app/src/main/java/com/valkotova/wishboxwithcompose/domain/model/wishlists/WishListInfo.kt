package com.valkotova.wishboxwithcompose.domain.model.wishlists

import com.valkotova.wishboxwithcompose.data.model.wishlists.WishListInfoData
import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_FileInfo
import com.valkotova.wishboxwithcompose.domain.model.users.UserInfo
import com.valkotova.wishboxwithcompose.domain.model.users.to_UserInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.to_WishShortInfo

data class WishListInfo(
    val id: Int?,
    val name: String,
    val visibility: Int,
    val user: Int,
    val preview: FileInfo?,
    val date: Long?,
    val isFavorite: Boolean,
    val viewers: List<UserInfo>?,
    val wishes: List<WishShortInfo>?
)

fun WishListInfoData.to_WishListInfo(currentUserId : Int, isMine : Boolean, defaultWishListName : String? = null) =
    WishListInfo(
        id = id,
        name = if(id == null && defaultWishListName != null) defaultWishListName else name,
        visibility = visibility,
        user = user,
        preview = preview?.to_FileInfo(),
        date = date,
        isFavorite = isFavorite,
        viewers = viewers?.map { it.to_UserInfo() },
        wishes = wishes?.map { it.to_WishShortInfo(currentUserId, isMine) }
    )