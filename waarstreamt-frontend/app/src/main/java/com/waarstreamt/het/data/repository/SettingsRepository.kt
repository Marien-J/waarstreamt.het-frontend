package com.waarstreamt.het.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    private object Keys {
        val LANGUAGE = stringPreferencesKey("language")
        val COUNTRY = stringPreferencesKey("country")
        val OWNED_SERVICES = stringSetPreferencesKey("owned_services")
    }

    val language: Flow<String> = dataStore.data.map { it[Keys.LANGUAGE] ?: "Nederlands" }
    val country: Flow<String> = dataStore.data.map { it[Keys.COUNTRY] ?: "Nederland" }
    val ownedServices: Flow<Set<String>> = dataStore.data.map { it[Keys.OWNED_SERVICES] ?: emptySet() }

    suspend fun setLanguage(value: String) {
        dataStore.edit { it[Keys.LANGUAGE] = value }
    }

    suspend fun setCountry(value: String) {
        dataStore.edit { it[Keys.COUNTRY] = value }
    }

    suspend fun setOwnedServices(services: Set<String>) {
        dataStore.edit { it[Keys.OWNED_SERVICES] = services }
    }
}
