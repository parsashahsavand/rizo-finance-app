package com.example.Parsa.domain.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.Parsa.data.datastore.PreferencesKeys
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    // حالت شب
    override val darkModeFlow: Flow<Boolean> =
        dataStore.data.map {
            it[PreferencesKeys.DARK_MODE] ?: false
        }

    override suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.DARK_MODE] = enabled
        }
    }

    //فعال بودن هوش
    override val activeAiFlow: Flow<Boolean> =
        dataStore.data.map {
            it[PreferencesKeys.ACTIVATE_AI] ?: true
        }

    override suspend fun setActiveAi(enabled: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.ACTIVATE_AI] = enabled
        }
    }

    //تعداد ستاره ها
    override val numberStarsFlow: Flow<Int> =
        dataStore.data.map {
            it[PreferencesKeys.NUMBER_STARS] ?: 0
        }

    override suspend fun setNumberStars(enabled: Int) {
        dataStore.edit {
            it[PreferencesKeys.NUMBER_STARS] = enabled
        }
    }

    //راهنما
    override val hasSeenTutorialFlow: Flow<Boolean> =
        dataStore.data.map {
            it[PreferencesKeys.HAS_SEEN_TUTORIAL] ?: false
        }

    override suspend fun setHasSeenTutorial(enabled: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.HAS_SEEN_TUTORIAL] = enabled
        }
    }
}
