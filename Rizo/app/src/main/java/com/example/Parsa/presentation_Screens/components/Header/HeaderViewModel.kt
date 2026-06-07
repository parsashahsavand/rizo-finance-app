package com.example.Parsa.presentation_Screens.components.Header

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Parsa.domain.repositories.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HeaderViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _isThemeLoaded = MutableStateFlow(false)
    val isThemeLoaded: StateFlow<Boolean> = _isThemeLoaded

    val darkMode: StateFlow<Boolean> =
        repository.darkModeFlow
            .onEach { _isThemeLoaded.value = true }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                false
            )

    fun toggleDarkMode(value: Boolean) {
        viewModelScope.launch {
            repository.setDarkMode(value)
        }
    }

    val activeAi: StateFlow<Boolean> =
        repository.activeAiFlow
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                true
            )

    fun toggleActiveAi(value: Boolean) {
        viewModelScope.launch {
            repository.setActiveAi(value)
        }
    }

    // مربوط به ستاره ها
    val numberStarsFlow: StateFlow<Int> =
        repository.numberStarsFlow
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                0
            )

    fun resetStars() {
        viewModelScope.launch {
          //  val current = repository.numberStarsFlow.first() // منتظر می‌مونه تا مقدار فعلی رو بگیره
            repository.setNumberStars(0)
        }
    }

    //راهنما
    val hasSeenTutorial: StateFlow<Boolean> =
        repository.hasSeenTutorialFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            false
        )

    fun completeTutorial() {
        viewModelScope.launch {
            repository.setHasSeenTutorial(true)
        }
    }
}
