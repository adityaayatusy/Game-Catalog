package com.aditya.dicoding.gamecatalog.core.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Suppress("unused")
class SettingPreference @Inject constructor(@ApplicationContext private val context: Context)  {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Setting")
    private val theme = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[theme] ?: false
        }
    }

    suspend fun saveThemeSetting(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[theme] = isDark
        }
    }
}