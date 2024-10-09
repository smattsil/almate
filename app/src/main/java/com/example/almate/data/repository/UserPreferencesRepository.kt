package com.example.almate.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.almate.domain.model.Credentials
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository (
    private val context: Context
) {

    // Keys for storing credentials
    private val SCHOOL_KEY = stringPreferencesKey("school")
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val PASSWORD_KEY = stringPreferencesKey("password")

    // Function to save credentials
    suspend fun saveCredentials(credentials: Credentials) {
        context.dataStore.edit { preferences ->
            preferences[SCHOOL_KEY] = credentials.school
            preferences[USERNAME_KEY] = credentials.username
            preferences[PASSWORD_KEY] = credentials.password
        }
    }

    // Function to read credentials
    val credentialsFlow = context.dataStore.data
        .map { preferences ->
            Credentials(
                school = preferences[SCHOOL_KEY] ?: "",
                username = preferences[USERNAME_KEY] ?: "",
                password = preferences[PASSWORD_KEY] ?: ""
            )
        }

    // Function to clear credentials (e.g., on logout)
    suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}
