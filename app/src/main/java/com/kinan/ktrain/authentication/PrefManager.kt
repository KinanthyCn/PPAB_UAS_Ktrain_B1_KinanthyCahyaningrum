package com.kinan.ktrain.authentication

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PrefManager private constructor(context: Context) {

        private val sharedPreferences: SharedPreferences

        companion object {
            private const val PREF_FILENAME = "AuthAppPrefs"
            private const val KEY_IS_LOGGED_IN = "isLoggedIn"
            private const val KEY_USERNAME = "username"
            private const val KEY_PASSWORD = "password"
            private const val KEY_ROLE = "role"
            private const val KEY_ID = "uid"

            @Volatile
            private var instance: PrefManager? = null

            fun getInstance(context: Context): PrefManager {
                return instance ?: synchronized(this) {
                    instance ?: PrefManager(context).also {
                        instance = it
                    }
                }
            }
        }

        init {
            sharedPreferences = context.getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE)
        }

        fun setLoggedIn(isLoggedIn: Boolean) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            editor.apply()
        }

        fun isLoggedIn(): Boolean {
            return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        }

        fun saveEmail (email: String) {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_USERNAME, email)
            editor.apply()
        }

        fun savePassword(password: String) {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_PASSWORD, password)
            editor.apply()
        }

        fun saveRole(role: String) {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_ROLE, role)
            editor.apply()
        }
        val role : String
            get() = sharedPreferences.getString(KEY_ROLE, "").toString()
        fun getEmail(): String? {
            return sharedPreferences.getString(KEY_USERNAME, "")
        }
    fun saveId(id: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ID, id)
        editor.apply()

    }
    fun getId(): String? {
        return sharedPreferences.getString(KEY_ID, "")

    }


        fun getPassword(): String? {
        return sharedPreferences.getString(KEY_PASSWORD, "")
        }

        fun clear() {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }



}