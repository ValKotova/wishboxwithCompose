package com.valkotova.wishboxwithcompose.data.network

import com.valkotova.wishboxwithcompose.data.model.requests.AddWishToListRequest
import com.valkotova.wishboxwithcompose.data.model.requests.CreateWishListRequest
import com.valkotova.wishboxwithcompose.data.model.requests.UpdateWishListRequest
import com.valkotova.wishboxwithcompose.data.model.responses.AllDataResponseForCalendar
import com.valkotova.wishboxwithcompose.data.model.responses.AllWishListsResponseData
import com.valkotova.wishboxwithcompose.data.model.responses.TokenResponse
import com.valkotova.wishboxwithcompose.data.model.wishlists.WishListInfoData
import retrofit2.Response
import retrofit2.http.*

interface WishlistAPI {

    @Headers("Accept: */*")
    @POST("wish/list/create")
    suspend fun createWishList(@Body body: CreateWishListRequest): Response<Unit>

    @Headers("Accept: */*")
    @PUT("wish/list/update")
    suspend fun updateWishList(@Body body: UpdateWishListRequest): Response<Unit>

    @Headers("Accept: */*")
    @PUT("wish/list/add/wish")
    suspend fun addWishToList(@Body body: AddWishToListRequest): Response<Unit>

    @GET("wish/list/info")
    suspend fun getWishListById(@Query("wishListId") wishListId: Int?, @Query("userId") userId: Int?): WishListInfoData

    @GET("wish/list/share")
    suspend fun getWishListLinkById(@Query("wishListId") wishListId: Int): TokenResponse

    @GET("wish/list/list")
    suspend fun getAllWishListsByUser(
        @Query("userId") userId: Int,
        @Query("page") page: Int,
        @Query("new") new : Int
    ): AllWishListsResponseData

    @GET("wish/list/calendar")
    suspend fun getAllWishListsByMonth(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): AllDataResponseForCalendar

    @Headers("Accept: */*")
    @DELETE("wish/list/remove")
    suspend fun removeMyWishList(@Query("wishListId") wishListId: Int): Response<Unit>

}