package com.valkotova.wishboxwithcompose.domain.Repositories

import com.valkotova.wishboxwithcompose.domain.model.common.MediaInfo
import com.valkotova.wishboxwithcompose.domain.model.common.MediaType
import java.io.File

interface UploadRepository {
    suspend fun uploadMedia(type: MediaType, file: File, fileName: String? = null): MediaInfo
}