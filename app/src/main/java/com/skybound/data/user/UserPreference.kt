package com.skybound.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.skybound.data.Roadmap2Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.sessiondataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: User) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    suspend fun saveRoadmap(roadmap : Roadmap2Item) {
        dataStore.edit { preferences ->
            preferences[ROADMAP_NAME] = roadmap.title
            preferences[ROADMAP_DL] = roadmap.date
            preferences[IS_ROADMAP] = true
        }
    }

    fun getRoadmap(): Flow<Roadmap2Item> {
        return dataStore.data.map { preferences ->
            Roadmap2Item(
                preferences[ROADMAP_NAME] ?: "",
                preferences[ROADMAP_DL] ?: "",
                preferences[IS_ROADMAP] ?: false
            )
        }
    }

    fun getSession(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ROADMAP_NAME = stringPreferencesKey("roadmapName")
        private val ROADMAP_DL = stringPreferencesKey("roadmapDL")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val IS_ROADMAP = booleanPreferencesKey("isRoadmap")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}