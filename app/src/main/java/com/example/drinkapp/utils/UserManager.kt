package com.example.drinkapp.utils

import android.content.Context

object UserManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_ROLE = "rose"

    data class UserData(var id: Long?,var role: Long?,var name:String?)

    fun saveUserInfo(id: Long,  role: Long,name: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(KEY_USER_ID, id)
        editor.putLong(KEY_ROLE,role)
        editor.putString(KEY_USER_NAME,name)
        editor.apply()
    }

    fun getUserInfo(context: Context): UserData {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val id = sharedPreferences.getLong(KEY_USER_ID, 0L)
        val role = sharedPreferences.getLong(KEY_ROLE,-2L)
        val userName = sharedPreferences.getString(KEY_USER_NAME,"")
        return UserData(id, role,userName)
    }

    fun clearUserInfo(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
