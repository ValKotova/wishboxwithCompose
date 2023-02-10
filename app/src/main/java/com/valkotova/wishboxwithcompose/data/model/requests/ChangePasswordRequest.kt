package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val token: String
)