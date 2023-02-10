package com.valkotova.wishboxwithcompose.data.repositories

import com.valkotova.wishboxwithcompose.data.model.requests.ChangePasswordRequest
import com.valkotova.wishboxwithcompose.data.model.requests.CredentialRequest
import com.valkotova.wishboxwithcompose.data.network.AuthAPI
import com.valkotova.wishboxwithcompose.data.utils.BaseBackendApi
import com.valkotova.wishboxwithcompose.domain.Repositories.AuthRepository
import com.valkotova.wishboxwithcompose.domain.model.Token
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val api : AuthAPI,
    val json : Json
) : BaseBackendApi(json), AuthRepository {
    override suspend fun registration(email: String, password: String): Token {
        return Token(safeApiCall { api.registration(CredentialRequest(email, password)) }.token)
    }

    override suspend fun login(email: String, password: String): Token {
        return Token(safeApiCall { api.login(CredentialRequest(email, password)) }.token)
    }

    override suspend fun requestCode(email: String) {
        return safeApiCallUnit { api.requestCode(email) }
    }

    override suspend fun confirmCode(email: String, code: String): Token {
        return Token(safeApiCall { api.confirmCode(email, code) }.token)
    }

    override suspend fun passwordUpdate(
        email: String,
        password: String,
        token: String
    ) {
        return safeApiCallUnit { api.passwordUpdate(ChangePasswordRequest(email, password, token)) }
    }

    override suspend fun requestEmailConfirmation() {
        return safeApiCallUnit { api.requestEmailConfirmation() }
    }
}