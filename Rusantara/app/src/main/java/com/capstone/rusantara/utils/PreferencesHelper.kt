package com.capstone.rusantara.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    fun clear() {
        editor.clear()
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "SESSION"
    }
}