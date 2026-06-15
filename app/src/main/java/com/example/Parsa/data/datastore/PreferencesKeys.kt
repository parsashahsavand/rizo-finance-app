package com.example.Parsa.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val DARK_MODE = booleanPreferencesKey("dark_mode")
    val ACTIVATE_AI = booleanPreferencesKey("Activate_Ai")
    val NUMBER_STARS = intPreferencesKey("Number_stars")
    val HAS_SEEN_TUTORIAL = booleanPreferencesKey("has_seen_tutorial")
}