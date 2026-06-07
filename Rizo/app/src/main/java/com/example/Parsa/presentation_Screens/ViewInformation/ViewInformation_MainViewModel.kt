package com.example.Parsa.presentation_Screens.ViewInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Parsa.domain.repositories.Repository_DB
import com.example.Parsa.presentation_Screens.components.List_components.TransactionItem
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ViewInformation_MainViewModel @Inject constructor(
    private val repository: Repository_DB
) : ViewModel() {

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

