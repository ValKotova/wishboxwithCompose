package com.valkotova.wishboxwithcompose.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserSettingsData(
    val autoAcceptRequests: Boolean,
    //val pushNotificationsEnable: Boolean,
    val canMakeWishBookers: BookAccess,
    val canSeeWishBookers: BookAccess,
    val showStatus : Boolean
)

enum class BookAccess{
    Everyone, Friends, FriendsOfFriends
}