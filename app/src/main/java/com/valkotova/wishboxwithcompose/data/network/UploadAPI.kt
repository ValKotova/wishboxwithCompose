package com.valkotova.wishboxwithcompose.data.network

import com.valkotova.wishboxwithcompose.data.model.common.MediaInfoData
import com.valkotova.wishboxwithcompose.domain.model.common.MediaType
import okhttp3.MultipartBody
import retrofit2.http.*

interface UploadAPI {

    @Headers("Accept: */*")
    @Multipart
    @POST("user/media/upload")
    suspend fun uploadMedia(@Query("type") type: MediaType, @Part file: MultipartBody.Part): MediaInfoData

}