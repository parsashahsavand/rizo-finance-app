package com.example.Parsa.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    // حالت شب
    val darkModeFlow: Flow<Boolean>
    suspend fun setDarkMode(enabled: Boolean)

    //فعال بودن هوش
    val activeAiFlow: Flow<Boolean>
    suspend fun setActiveAi(enabled: Boolean)

    //تعداد ستاره ها
    val numberStarsFlow: Flow<Int>
    suspend fun setNumberStars(enabled: Int)

    //راهنما
    val hasSeenTutorialFlow: Flow<Boolean>
    suspend fun setHasSeenTutorial(enabled: Boolean)
}

