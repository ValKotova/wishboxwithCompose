package com.valkotova.wishboxwithcompose.data.model.users

import com.valkotova.wishboxwithcompose.data.model.UserSettingsData
import com.valkotova.wishboxwithcompose.data.model.common.FileInfoData
import com.valkotova.wishboxwithcompose.data.model.common.LinkInfoData
import kotlinx.serialization.Serializable

@Serializable
data class CredentialsInfoData(
    val id: Int?,
    val avatar: FileInfoData?,
    val email: String?,
    val confirmed: Boolean,
    val nickname: String?,
    val fullName: String?,
    val gender: String?,
    val description: String?,
    val socialLink: List<LinkInfoData>?,
    val birthDate: String?,
    val lists: Int?,
    val settings: UserSettingsData?,
    val subscribers: Int?,
    val subscriptions: Int?,
    val public: Boolean?,
)