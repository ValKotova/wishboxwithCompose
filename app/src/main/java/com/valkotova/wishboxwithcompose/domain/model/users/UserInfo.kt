package com.valkotova.wishboxwithcompose.domain.model.users

import com.valkotova.wishboxwithcompose.data.model.users.SubEnum
import com.valkotova.wishboxwithcompose.data.model.users.UserInfoData
import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.LinkInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_LinkInfo
import java.text.SimpleDateFormat

data class UserInfo(
    val id: Int,
    val avatar: FileInfo?,
    val nickname: String?,
    val fullName: String,
    val public: Boolean,
    val sub: SubEnum?,
    val birthDate: Long?,
    val description: String?,
    val gender: Gender?,
    val socialLink: List<LinkInfo>?,
    val lists: Int?,
    val subscribers: Int?,
    val subscriptions: Int?,
)

fun UserInfoData.to_UserInfo() = UserInfo(
    id = this.id,
    avatar = this.avatar?.to_FileInfo(),
    nickname = this.nickname,
    fullName = this.fullName,
    public = this.public,
    sub = this.sub,
    birthDate = birthDate?.let {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        try {
            formatter.parse(it)?.time
        } catch (e : Exception){
            null
        }
    },
    description = this.description,
    gender = Gender.valueOf(this.gender?:"unknown"),
    socialLink = this.socialLink?.map { it -> it.to_LinkInfo()},
    lists = this.lists,
    subscribers = this.subscribers,
    subscriptions = this.subscriptions

)