package com.valkotova.wishboxwithcompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val token: String
)