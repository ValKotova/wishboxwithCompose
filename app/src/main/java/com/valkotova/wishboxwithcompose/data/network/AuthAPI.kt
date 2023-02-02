package com.valkotova.wishboxwithcompose.data.network

import com.valkotova.wishboxwithcompose.data.model.ChangePasswordRequest
import com.valkotova.wishboxwithcompose.data.model.CredentialRequest
import com.valkotova.wishboxwithcompose.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {

    @POST("user/registration")
    suspend fun registration(@Body body: CredentialRequest): TokenResponse

    @POST("user/login")
    suspend fun login(@Body body: CredentialRequest): TokenResponse

    @Headers("Accept: */*")
    @GET("user/password/recovery")
    suspend fun requestCode(@Query("email") email: String): Response<Unit>

    @GET("user/password/confirm")
    suspend fun confirmCode(
        @Query("email") email: String,
        @Query("code") code: String
    ): TokenResponse

    @Headers("Accept: */*")
    @PUT("user/password/update")
    suspend fun passwordUpdate(@Body body: ChangePasswordRequest): Response<Unit>

    @Headers("Accept: */*")
    @GET("user/email/send")
    suspend fun requestEmailConfirmation(): Response<Unit>
}