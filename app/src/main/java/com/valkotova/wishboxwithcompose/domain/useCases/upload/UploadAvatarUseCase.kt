package com.valkotova.wishboxwithcompose.domain.useCases.upload

import com.valkotova.wishboxwithcompose.domain.Repositories.UploadRepository
import com.valkotova.wishboxwithcompose.domain.model.common.MediaInfo
import com.valkotova.wishboxwithcompose.domain.model.common.MediaType
import java.io.File
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val uploadRepository: UploadRepository
){
    suspend fun execute(file: File, filename: String? = null): MediaInfo {
        return uploadRepository.uploadMedia(MediaType.AVATAR, file, filename)
    }
}