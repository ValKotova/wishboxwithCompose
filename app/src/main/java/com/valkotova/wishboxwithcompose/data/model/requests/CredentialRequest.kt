package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class CredentialRequest(
    val email: String,
    val password: String
)
