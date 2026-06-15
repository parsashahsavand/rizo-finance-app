package com.example.Parsa.domain.repositories

import com.example.Parsa.data.local.entity.Challenge
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings
import com.example.Parsa.presentation_Screens.components.List_components.TransactionItem
import kotlinx.coroutines.flow.Flow

interface Repository_DB {
    suspend fun addCost(costs: Costs)
    suspend fun addIncome(income: Income)
    suspend fun addNote(note: Note)
    suspend fun addSavings(savings: Savings)
    suspend fun addDebt(debt: Debt)
    suspend fun addRequest(request: Request)

    //نشون دادن همه
    fun getAllTransactions(): Flow<List<TransactionItem>>

    // صفحه مشخصات
    suspend fun getIncomeById(id: Int): Income?
    suspend fun getCostById(id: Int): Costs?
    suspend fun getNoteById(id: Int): Note?
    suspend fun getSavingsById(id: Int): Savings?
    suspend fun getDebtById(id: Int): Debt?
    suspend fun getRequestById(id: Int): Request?

    //حذف
    suspend fun deleteCost(costs: Costs)
    suspend fun deleteIncome(income: Income)
    suspend fun deleteNote(note: Note)
    suspend fun deleteSavings(savings: Savings)
    suspend fun deleteDebt(debt: Debt)
    suspend fun deleteRequest(request: Request)

    //محاسبات
    fun getTotalIncome(from: Long, to: Long): Flow<Long?>
    fun getTotalCosts(from: Long, to: Long): Flow<Long?>
    fun getTotalSavings(from: Long, to: Long): Flow<Long?>
    fun getTotalDebt(from: Long, to: Long): Flow<Long?>
    fun getTotalRequests(from: Long, to: Long): Flow<Long?>

    //نمودار
    fun getMonthlyCosts(from: Long, to: Long): Flow<Long>
    fun getMonthlyIncome(from: Long, to: Long): Flow<Long>
    fun getMonthlySavings(from: Long, to: Long): Flow<Long>

    //چالش ها
    fun getAllChallenges(): Flow<List<Challenge>>
    suspend fun addChallenge(challenge: Challenge)
}