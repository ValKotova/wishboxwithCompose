package com.valkotova.wishboxwithcompose.domain.Repositories

import android.media.session.MediaSession
import com.valkotova.wishboxwithcompose.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun registration(email: String, password: String): Token

    suspend fun login(email: String, password: String): Token

    suspend fun requestCode(email: String)

    suspend fun confirmCode(email: String, code: String): Token

    suspend fun passwordUpdate(
        email: String,
        password: String,
        token: String
    )

    suspend fun requestEmailConfirmation()

}