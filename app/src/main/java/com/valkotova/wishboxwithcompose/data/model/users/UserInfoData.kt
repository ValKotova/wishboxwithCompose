package com.valkotova.wishboxwithcompose.data.model.users

import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData
import com.valkotova.wishboxwithcompose.data.model.common.LinkInfoData
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoData(
    val id: Int,
    val avatar: FileInfoData?,
    val nickname: String?,
    val fullName: String,
    val public: Boolean,
    val sub: SubEnum?,
    val birthDate: String?,
    val description: String?,
    val gender: String?,
    val socialLink: List<LinkInfoData>?,
    val lists: Int?,
    val subscribers: Int?,
    val subscriptions: Int?,
)

enum class SubEnum{
    FREE, REQUESTED, SUBSCRIBER
}
