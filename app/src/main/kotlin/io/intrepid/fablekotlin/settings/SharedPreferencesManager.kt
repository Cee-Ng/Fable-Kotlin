package io.intrepid.fablekotlin.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesManager private constructor(context: Context) : UserSettings {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        private val LAST_IP = "last_ip"
        private val TOKEN_KEY = "token"
        private val USER_ID_KEY = "id"
        private val USER_IMAGE_KEY = "image"
        private val USER_NAME_KEY = "name"
        private var instance: UserSettings? = null
        fun getInstance(context: Context): UserSettings {
            return instance ?: SharedPreferencesManager(context).apply { instance = this }
        }
    }

    override var lastIp: String
        get() = preferences.getString(LAST_IP, "")
        set(value) = preferences.edit().putString(LAST_IP, value).apply()

    override var token: String?
        get() = preferences.getString(TOKEN_KEY, "")
        set(value) = preferences.edit().putString(LAST_IP, value).apply()

    override var userId: String?
        get() = preferences.getString(USER_ID_KEY, "")
        set(value) = preferences.edit().putString(USER_ID_KEY, value).apply()

    override var userImage: String?
        get() = preferences.getString(USER_IMAGE_KEY, "")
        set(value) = preferences.edit().putString(USER_IMAGE_KEY, value).apply()

    override var userName: String?
        get() = preferences.getString(USER_NAME_KEY, "")
        set(value) = preferences.edit().putString(USER_NAME_KEY, value).apply()
}

