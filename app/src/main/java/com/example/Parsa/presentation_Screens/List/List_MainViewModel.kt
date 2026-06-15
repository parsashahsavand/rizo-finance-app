package com.example.Parsa.presentation_Screens.List

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
class List_MainViewModel @Inject constructor(
    private val repository: Repository_DB
) : ViewModel() {
    private val _isLoading =
        MutableStateFlow(true) // شروع با true چون لیست در ابتدا خالیه و داره لود میشه
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

//    val transactions = repository
//        .getAllTransactions()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )

    fun deleteCost(costs: Costs) = viewModelScope.launch {
        repository.deleteCost(costs)
    }

    fun deleteIncome(income: Income) = viewModelScope.launch {
        repository.deleteIncome(income)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun deleteSavings(savings: Savings) = viewModelScope.launch {
        repository.deleteSavings(savings)
    }

    fun deleteDebt(debt: Debt) = viewModelScope.launch {
        repository.deleteDebt(debt)
    }

    fun deleteRequest(request: Request) = viewModelScope.launch {
        repository.deleteRequest(request)
    }

    private val _filter = MutableStateFlow(TransactionFilter())
    val filter = _filter.asStateFlow()

    val _isActiveFilter = MutableStateFlow(false)
    val isActiveFilter: StateFlow<Boolean> = _isActiveFilter

    val transactions = combine(
        repository.getAllTransactions(),
        filter
    ) { items, filter ->
        items
            .filter { filter.type == null || it.type == filter.type }
            .filter { !filter.onlyImportant || it.isImportant }
            .filter { !filter.onlyRecurring || it.repetition }
            .filter {
                filter.fromDate == null ||
                        persianTextToMillis(it.dateText) >= filter.fromDate
            }
            .filter {
                filter.toDate == null ||
                        persianTextToMillis(it.dateText) <= filter.toDate
            }
            .filter { item ->
                filter.minAmount == null || item.amountText.toIntOrNull()?.let { amount ->
                    amount >= filter.minAmount
                } ?: false
            }
            .filter { item ->
                filter.maxAmount == null || item.amountText.toIntOrNull()?.let { amount ->
                    amount <= filter.maxAmount
                } ?: false
            }
            .filter { item ->
                filter.category == null || filter.category == "پیش فرز" || filter.category == item.category
            }
            .filter { item ->
                filter.payType == null || filter.payType == "پیش فرز" || filter.payType == item.payType
            }

    }
        .onEach { // اضافه کردن onEach برای مدیریت isLoading
            _isLoading.value = false // وقتی لیست آماده شد، isLoading رو false کن
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun applyFilter(newFilter: TransactionFilter) {
        _filter.value = newFilter
        _isActiveFilter.value = !newFilter.isEmpty()
    }

    fun clearFilter() {
        _filter.value = TransactionFilter()
        _isActiveFilter.value = false
    }
}