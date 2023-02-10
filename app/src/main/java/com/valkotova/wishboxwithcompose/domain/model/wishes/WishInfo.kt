package com.valkotova.wishboxwithcompose.domain.model.wishes

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishInfoData
import com.valkotova.wishboxwithcompose.data.model.Wishes.WishStatus
import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_FileInfo
import com.valkotova.wishboxwithcompose.domain.model.users.UserInfo
import com.valkotova.wishboxwithcompose.domain.model.users.to_UserInfo

data class WishInfo(
    val id: Int,
    val name: String,
    val previews: List<FileInfo>,
    val booker: UserInfo?,
    val comment: String?,
    val favorite: Boolean,
    val link: String?,
    val list: Int?,
    val status: WishStatus,
    val price: String?,
    val user: Int
){
}

fun WishInfoData.to_WishInfo() = WishInfo(
    id = this.id,
    name = this.name,
    previews = this.previews.map { it.to_FileInfo() },
    booker = this.booker?.to_UserInfo(),
    comment = this.comment,
    favorite = this.favorite,
    link = this.link,
    list = this.list,
    status = this.status,
    price = String.format("%.2f", this.price),
    user = this.user

)