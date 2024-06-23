package com.ibrahimofick.github.SETTING

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesSetting private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun ThemeSetting(): Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[THEME_KEY] ?: false }

    suspend fun saveTheme(isDarkModeActive: Boolean) {
        dataStore.edit { preferences -> preferences[THEME_KEY] = isDarkModeActive }
    }

    companion object {
        @Volatile
        private var INSTANCE: PreferencesSetting? = null

        fun getInstance(dataStore: DataStore<Preferences>): PreferencesSetting =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesSetting(dataStore).also { INSTANCE = it }
            }
    }
}