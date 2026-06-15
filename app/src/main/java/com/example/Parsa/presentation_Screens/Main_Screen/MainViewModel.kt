package com.example.Parsa.presentation_Screens.Main_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Parsa.domain.repositories.Repository_DB
import com.example.Parsa.domain.repositories.SettingsRepository
import com.example.Parsa.presentation_Screens.components.Main_components.getMonthRangeMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SettingsRepository,
    private val repository_db: Repository_DB
) : ViewModel() {
    val activeAi: StateFlow<Boolean> =
        repository.activeAiFlow
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                true
            )

    fun loadMonthlyData(from: Long, to: Long): StateFlow<Triple<Long, Long, Long>> =
        combine(
            repository_db.getMonthlyCosts(from, to),
            repository_db.getMonthlySavings(from, to),
            repository_db.getMonthlyIncome(from, to)
        ) { cost, saving, income ->
            Triple(cost, saving, income)
        }
            .distinctUntilChanged()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                Triple(0L, 0L, 0L)
            )

    fun loadYearlyData(year: Int): Flow<List<Triple<Long, Long, Long>>> = flow {
        val result = mutableListOf<Triple<Long, Long, Long>>()

        for (month in 1..12) {
            val (from, to) = getMonthRangeMillis(year, month)

            val cost = repository_db.getMonthlyCosts(from, to).first()
            val saving = repository_db.getMonthlySavings(from, to).first()
            val income = repository_db.getMonthlyIncome(from, to).first()

            result.add(Triple(cost, saving, income))
        }

        emit(result)
    }

    //محاسبات
    private val _totalCosts = MutableStateFlow<Long?>(null)
    val totalCosts = _totalCosts.asStateFlow()

    private val _totalIncome = MutableStateFlow<Long?>(null)
    val totalIncome= _totalIncome.asStateFlow()

    private val _totalSavings = MutableStateFlow<Long?>(null)
    val totalSavings= _totalSavings.asStateFlow()

    private val _totalDebt = MutableStateFlow<Long?>(null)
    val totalDebt= _totalDebt.asStateFlow()

    private val _totalRequests = MutableStateFlow<Long?>(null)
    val totalRequests= _totalRequests.asStateFlow()

    fun loadTotalCosts(year: Int, month: Int) {
        val (from, to) = getMonthRangeMillis(year, month)
        viewModelScope.launch {
            repository_db.getTotalCosts(from, to)
                .collect { value ->
                    _totalCosts.value = value ?: 0L
                }
        }
    }

    fun loadTotalIncome(year: Int, month: Int) {
        val (from, to) = getMonthRangeMillis(year, month)
        viewModelScope.launch {
            repository_db.getTotalIncome(from, to)
                .collect { value ->
                    _totalIncome.value = value ?: 0L
                }
        }
    }

    fun loadTotalSavings(year: Int, month: Int) {
        val (from, to) = getMonthRangeMillis(year, month)
        viewModelScope.launch {
            repository_db.getTotalSavings(from, to)
                .collect { value ->
                    _totalSavings.value = value ?: 0L
                }
        }
    }

    fun loadTotalDebt(year: Int, month: Int) {
        val (from, to) = getMonthRangeMillis(year, month)
        viewModelScope.launch {
            repository_db.getTotalDebt(from, to)
                .collect { value ->
                    _totalDebt.value = value ?: 0L
                }
        }
    }

    fun loadTotalRequests(year: Int, month: Int) {
        val (from, to) = getMonthRangeMillis(year, month)
        viewModelScope.launch {
            repository_db.getTotalRequests(from, to)
                .collect { value ->
                    _totalRequests.value = value ?: 0L
                }
        }
    }
}