package com.example.Parsa.presentation_Screens.components.Main_components.Added_to_homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings
import com.example.Parsa.domain.repositories.Repository_DB
import com.example.Parsa.presentation_Screens.Filter.TransactionFilter
import com.example.Parsa.presentation_Screens.components.persianTextToMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class Added_to_homepageViewModel @Inject constructor(
    private val repository: Repository_DB
) : ViewModel() {
    fun deleteCost(costs: Costs) = viewModelScope.launch {
        repository.deleteCost(costs)
    }

    fun deleteIncome(income: Income) = viewModelScope.launch {
        repository.deleteIncome(income)
    }

    private val _filter = MutableStateFlow(
        TransactionFilter(
            onlyRecurring = true
        )
    )
    val filter = _filter.asStateFlow()

    val transactions = combine(
        repository.getAllTransactions(),
        filter
    ) { items, filter ->
        items
            .filter { !filter.onlyRecurring || it.repetition }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}