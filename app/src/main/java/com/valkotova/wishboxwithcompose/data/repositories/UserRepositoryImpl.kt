package com.valkotova.wishboxwithcompose.data.repositories

import com.valkotova.wishboxwithcompose.data.model.requests.InitProfileRequest
import com.valkotova.wishboxwithcompose.data.network.UserAPI
import com.valkotova.wishboxwithcompose.data.utils.BaseBackendApi
import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.model.users.CredentialsInfo
import com.valkotova.wishboxwithcompose.domain.model.users.UserInfo
import com.valkotova.wishboxwithcompose.domain.model.users.to_CredentialsInfo
import com.valkotova.wishboxwithcompose.domain.model.users.to_UserInfo
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userAPI: UserAPI,
                                             json: Json)
    : UserRepository, BaseBackendApi(json) {
    override suspend fun initProfile(
        nickname: String?,
        fullName: String,
        gender: String,
        birthday: String?,
        about: String?,
        social: List<String>?,
        avatar: Int?
    ) {
        safeApiCallUnit { userAPI.initProfile(
            InitProfileRequest(
                avatar,
                nickname,
                fullName,
                gender,
                birthday,
                about,
                social
            ))
        }
    }

    override suspend fun me(): CredentialsInfo {
        return safeApiCall {
            userAPI.me()
        }.to_CredentialsInfo()
    }

    override suspend fun getOtherProfileById(id: Int): UserInfo {
        return safeApiCall {
            userAPI.getOtherProfile(id)
        }.to_UserInfo()
    }
}