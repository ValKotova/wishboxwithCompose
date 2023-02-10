package com.valkotova.wishboxwithcompose.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.valkotova.wishboxwithcompose.data.network.UploadAPI
import com.valkotova.wishboxwithcompose.data.network.UserAPI
import com.valkotova.wishboxwithcompose.data.utils.BaseBackendApi
import com.valkotova.wishboxwithcompose.domain.Repositories.UploadRepository
import com.valkotova.wishboxwithcompose.domain.model.common.MediaInfo
import com.valkotova.wishboxwithcompose.domain.model.common.MediaType
import com.valkotova.wishboxwithcompose.domain.model.common.to_MediaInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class UploadRepositoryImpl @Inject constructor(private val uploadAPI: UploadAPI,
                                               json: Json
)
    : UploadRepository, BaseBackendApi(json)  {
    override suspend fun uploadMedia(
        type: MediaType,
        file: File,
        fileName: String?
    ): MediaInfo {
        val fileData = withContext(Dispatchers.Default) {
            val name = fileName ?: if (type == MediaType.WISH) file.getFileName() else file.name
            val bMap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val out = ByteArrayOutputStream()
            bMap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            val requestFile = out.toByteArray().toRequestBody("image/jpeg".toMediaType())
            MultipartBody.Part.createFormData(
                "file",
                name,
                requestFile
            )
        }
        return safeApiCall { uploadAPI.uploadMedia(type, fileData) }.to_MediaInfo()
    }
}

fun File.getFileName(): String {
    val filename = name
    return if (filename.endsWith(".")) {
        filename.plus("jpg")
    } else {
        filename.plus(".jpg")
    }
}