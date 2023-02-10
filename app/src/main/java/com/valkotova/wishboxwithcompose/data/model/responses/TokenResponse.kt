package com.valkotova.wishboxwithcompose.data.model.responses

import com.valkotova.wishboxwithcompose.domain.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String
)

fun TokenResponse.toToken() = Token(token = token)