package com.valkotova.wishboxwithcompose.data.repositories

import com.valkotova.wishboxwithcompose.data.prefs.UserData
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor (private val userData: UserData) : PrefsRepository {

    override fun setToken(token: String) {
        userData.setToken(token)
    }

    override fun getToken(): String {
        return userData.getToken()
    }

    override fun setMyId(id: Int) {
        userData.setMyId(id)
    }

    override fun getMyId(): Int {
        return userData.getMyId()
    }
    override fun logout() {
        userData.logout()
    }

}