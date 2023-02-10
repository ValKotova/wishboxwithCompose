package com.valkotova.wishboxwithcompose.domain.Repositories

import com.valkotova.wishboxwithcompose.domain.model.users.CredentialsInfo
import com.valkotova.wishboxwithcompose.domain.model.users.UserInfo

interface UserRepository {
    suspend fun initProfile(
        nickname: String?,
        fullName: String,
        gender: String,
        birthday: String?,
        about: String?,
        social: List<String>?,
        avatar: Int?
    )

    suspend fun me(): CredentialsInfo

    suspend fun getOtherProfileById(id: Int): UserInfo
}