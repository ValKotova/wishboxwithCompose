package com.valkotova.wishboxwithcompose.data.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserData @Inject constructor(@ApplicationContext private val context: Context)  {

    private val APP_PREFERENCES = "apppreferences"
    private val TOKEN = "token"
    private val MY_ID = "myid"

    private val sharedPreferences: SharedPreferences by lazy { context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE) }

    fun setToken(token: String){
        putValue(TOKEN, token)
    }

    fun getToken(): String = sharedPreferences.getString(TOKEN, "") ?: ""

    fun setMyId(id: Int){
        putValue(MY_ID, id)
    }

    fun getMyId(): Int = sharedPreferences.getInt(MY_ID, 0)

    fun logout(){
        sharedPreferences.edit().clear().apply()
    }

    private fun <T> putValue(key: String, value: T) {
        val editor = sharedPreferences.edit()
        when(value){
            is String -> {
                editor.putString(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }
            else -> throw RuntimeException("try to put wrong type to preferences")
        }
        editor.apply()
    }

}