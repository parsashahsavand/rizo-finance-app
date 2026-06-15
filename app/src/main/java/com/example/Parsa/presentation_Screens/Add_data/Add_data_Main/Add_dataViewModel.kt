package com.example.Parsa.presentation_Screens.Add_data.Add_data_Main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings
import com.example.Parsa.domain.repositories.Repository_DB
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class Add_dataViewmodel @Inject constructor(
    private val repository : Repository_DB
): ViewModel() {
    fun addCost(costs: Costs) = viewModelScope.launch {
        repository.addCost(costs)
    }

    fun addIncome(income: Income) = viewModelScope.launch {
        repository.addIncome(income)
    }

    fun addNote(note: Note) = viewModelScope.launch {
        repository.addNote(note)
    }

    fun addSavings(savings: Savings) = viewModelScope.launch {
        repository.addSavings(savings)
    }

    fun addDebt(debt: Debt) = viewModelScope.launch {
        repository.addDebt(debt)
    }

    fun addRequest(request: Request) = viewModelScope.launch {
        repository.addRequest(request)
    }

    private val _data = MutableStateFlow<Any?>(null)
    val data = _data.asStateFlow()

    fun load(id: Int, type: TransactionType) {
        viewModelScope.launch {
            _data.value = when (type) {
                TransactionType.INCOME -> repository.getIncomeById(id)
                TransactionType.COST -> repository.getCostById(id)
                TransactionType.NOTE -> repository.getNoteById(id)
                TransactionType.SAVINGS -> repository.getSavingsById(id)
                TransactionType.DEBT -> repository.getDebtById(id)
                TransactionType.REQUEST -> repository.getRequestById(id)
            }
        }
    }
}