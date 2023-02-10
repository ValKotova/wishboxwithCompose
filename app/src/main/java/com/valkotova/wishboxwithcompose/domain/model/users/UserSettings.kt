package com.valkotova.wishboxwithcompose.domain.model.users

import com.valkotova.wishboxwithcompose.data.model.BookAccess
import com.valkotova.wishboxwithcompose.data.model.UserSettingsData

data class UserSettings(
    val autoAcceptRequests: Boolean,
    val canMakeWishBookers: BookAccess,
    val canSeeWishBookers: BookAccess,
    val showStatus : Boolean
)

fun UserSettingsData.to_UserSettings() = UserSettings(
    autoAcceptRequests = this.autoAcceptRequests,
    canMakeWishBookers = this.canSeeWishBookers,
    canSeeWishBookers = this.canSeeWishBookers,
    showStatus = this.showStatus
)