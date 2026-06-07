package com.example.Parsa.presentation_Screens.components.Main_components.Challenges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Parsa.data.local.entity.Challenge
import com.example.Parsa.domain.repositories.Repository_DB
import com.example.Parsa.domain.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ChallengesViewModel @Inject constructor(
    private val repository: SettingsRepository,
    private val repository_db: Repository_DB
) : ViewModel() {
    val allChallenges: Flow<List<Challenge>> = repository_db.getAllChallenges()

    fun upsertChallenge(challenge: Challenge) {
        viewModelScope.launch {
            repository_db.addChallenge(challenge)
        }
    }

    val numberStarsFlow: StateFlow<Int> =
        repository.numberStarsFlow
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    fun incrementStars() {
        viewModelScope.launch {
            val current = repository.numberStarsFlow.first() // منتظر می‌مونه تا مقدار فعلی رو بگیره
            repository.setNumberStars(current + 1)
        }
    }
}