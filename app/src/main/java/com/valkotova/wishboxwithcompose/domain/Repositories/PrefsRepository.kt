package com.valkotova.wishboxwithcompose.domain.Repositories

interface PrefsRepository {

    fun setToken(token: String)

    fun getToken(): String

    fun setMyId(id: Int)

    fun getMyId(): Int

    fun logout()
}