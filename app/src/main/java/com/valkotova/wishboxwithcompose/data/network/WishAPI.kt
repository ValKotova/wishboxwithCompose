package com.valkotova.wishboxwithcompose.data.network

import com.valkotova.wishboxwithcompose.data.model.Wishes.WishInfoData
import com.valkotova.wishboxwithcompose.data.model.requests.*
import com.valkotova.wishboxwithcompose.data.model.responses.WishCreationResponseData
import retrofit2.Response
import retrofit2.http.*

interface WishAPI {

    @Headers("Accept: */*")
    @POST("wish/create")
    suspend fun createWish(@Body body: CreateWishRequest): WishCreationResponseData

    @GET("wish/info")
    suspend fun getWishById(@Query("wishId") wishId: Int): WishInfoData

    @Headers("Accept: */*")
    @PUT("wish/update")
    suspend fun updateWish(@Body body: UpdateWishRequest): Response<Unit>

    @Headers("Accept: */*")
    @PUT("wish/status/set")
    suspend fun setWishStatus(@Body body: UpdateWishStatusRequest): Response<Unit>

    @Headers("Accept: */*")
    @PUT("wish/status/drop")
    suspend fun dropWishStatus(@Body body: DropWishStatusRequest): Response<Unit>

    @GET("wish/my/all")
    suspend fun getAllMyWishes(@Query("name") name: String, @Query("page") page: Int): WishesListResponseData

    @Headers("Accept: */*")
    @DELETE("wish/remove")
    suspend fun removeMyWish(@Query("wishId") wishId: Int): Response<Unit>

    @Headers("Accept: */*")
    @DELETE("wish/favorite")
    suspend fun removeFromFavorites(@Query("id") wishId: Int): Response<Unit>

    @Headers("Accept: */*")
    @GET("wish/favorite")
    suspend fun addToFavorites(@Query("id") wishId: Int): Response<Unit>

}