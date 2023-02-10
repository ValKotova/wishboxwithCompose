package com.valkotova.wishboxwithcompose.data.network

import com.valkotova.wishboxwithcompose.data.model.requests.InitProfileRequest
import com.valkotova.wishboxwithcompose.data.model.users.CredentialsInfoData
import com.valkotova.wishboxwithcompose.data.model.users.UserInfoData
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @GET("user/me")
    suspend fun me(): CredentialsInfoData

    @Headers("Accept: */*")
    @POST("user/init")
    suspend fun initProfile(@Body body: InitProfileRequest): Response<Unit>

    @GET("user/user")
    suspend fun getOtherProfile(@Query("userId") userId: Int): UserInfoData
}