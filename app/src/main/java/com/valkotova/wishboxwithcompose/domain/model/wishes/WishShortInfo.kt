package com.valkotova.wishboxwithcompose.domain.model.wishes

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishShortInfoData
import com.valkotova.wishboxwithcompose.data.model.Wishes.WishStatus
import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_FileInfo
import com.valkotova.wishboxwithcompose.ui.main.getGlideUrl

data class WishShortInfo(
    val id: Int,
    val name: String,
    val preview: FileInfo?,
    val status: WishStatus,
    val favorite : Boolean,
    val booker : Int?,
    val isBlocked : Boolean = false,
    val isBooked : Boolean = false
)

fun WishShortInfoData.to_WishShortInfo(id : Int, isMine : Boolean) = WishShortInfo(
    id = this.id,
    name = this.name,
    preview = this.preview?.to_FileInfo(),
    status = this.status,
    favorite = this.favorite,
    booker = this.booker,
    isBlocked = this.booker != id && !isMine,
    isBooked = this.booker == id && !isMine,
)