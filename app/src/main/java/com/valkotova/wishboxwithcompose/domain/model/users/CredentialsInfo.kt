package com.valkotova.wishboxwithcompose.domain.model.users

import com.valkotova.wishboxwithcompose.data.model.users.CredentialsInfoData
import com.valkotova.wishboxwithcompose.domain.model.common.FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.LinkInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_FileInfo
import com.valkotova.wishboxwithcompose.domain.model.common.to_LinkInfo
import java.nio.file.Files.find
import java.text.SimpleDateFormat

data class CredentialsInfo(
    val id: Int?,
    val avatar: FileInfo?,
    val email: String?,
    val confirmed: Boolean,
    val nickname: String?,
    val fullName: String?,
    val gender: Gender?,
    val description: String?,
    val socialLink: List<LinkInfo>?,
    val birthDate: Long?,
    val lists: Int?,
    val settings: UserSettings?,
    val subscribers: Int?,
    val subscriptions: Int?,
    val public: Boolean?,
)

enum class Gender{
    male, female, unknown
}

fun CredentialsInfoData.to_CredentialsInfo() = CredentialsInfo(
    id = this.id,
    avatar = this.avatar?.to_FileInfo(),
    email = this.email,
    confirmed = this.confirmed,
    nickname = this.nickname,
    fullName = this.fullName,
    gender = Gender.valueOf(this.gender?:"unknown"),
    description = this.description,
    socialLink = this.socialLink?.map { it -> it.to_LinkInfo()},
    birthDate = birthDate?.let {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        try {
            formatter.parse(it)?.time
        } catch (e : Exception){
            null
        }
    },
    lists = this.lists,
    settings = this.settings?.to_UserSettings(),
    subscribers = this.subscribers,
    subscriptions = this.subscriptions,
    public = this.public,
)