package com.valkotova.wishboxwithcompose.data.model.requests

import kotlinx.serialization.Serializable


@Serializable
data class InitProfileRequest(
    val avatar: Int?,
    val nickname: String?,
    val fullName: String,
    val gender: String,
    val birthDate: String?,
    val description: String?,
    val socialLink: List<String>?,
)
