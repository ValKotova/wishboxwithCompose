package com.valkotova.wishboxwithcompose.domain.model.wishlists

import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.domain.model.users.UserInfo
import com.valkotova.wishboxwithcompose.domain.model.wishes.WishShortInfo

data class DataForCreateWishListRequest(
    val name: String = "",
    val wishes: List<WishShortInfo> = listOf(),
    val viewers: List<UserInfo> = listOf(),
    val visibility: Int = 0,
    val date: Long? = null,
    val preview: FileInfo? = null
)