package com.example.Parsa.domain.repositories

import com.example.Parsa.data.local.dao.AppDao
import com.example.Parsa.data.local.entity.Challenge
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings
import com.example.Parsa.presentation_Screens.components.List_components.TransactionItem
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import com.example.Parsa.presentation_Screens.components.formatDate
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class RepositoryImpl_DB @Inject constructor(
    private val dao: AppDao
) : Repository_DB {
    //اضافه کردن ها
    override suspend fun addCost(costs: Costs) = dao.upsertCost(costs)
    override suspend fun addIncome(income: Income) = dao.upsertIncome(income)
    override suspend fun addNote(note: Note) = dao.upsertNote(note)
    override suspend fun addSavings(savings: Savings) = dao.upsertSavings(savings)
    override suspend fun addDebt(debt: Debt) = dao.upsertDebt(debt)
    override suspend fun addRequest(request: Request) = dao.upsertRequest(request)

    //نشون دادن همه
    override fun getAllTransactions(): Flow<List<TransactionItem>> {
        return combine(
            dao.getAllCosts(),
            dao.getAllIncome(),
            dao.getAllNotes(),
            dao.getAllSavings(),
            dao.getAllDebts(),
            dao.getAllRequests()
        ) { results ->
            val costs = results[0] as List<Costs>
            val incomes = results[1] as List<Income>
            val notes = results[2] as List<Note>
            val savings = results[3] as List<Savings>
            val debts = results[4] as List<Debt>
            val requests = results[5] as List<Request>

            val all = mutableListOf<TransactionItem>()

            all += costs.map {
                TransactionItem(
                    id = it.id,
                    type = TransactionType.COST,
                    title = it.title,
                    dateText = formatDate(it.date),
                    amountText = it.amount.toString(),
                    isImportant = it.isImportant,
                    category = it.category,
                    payType = it.payment_type,
                    repetition = it.isRecurring,
                    description = it.description.toString()
                )
            }

            all += incomes.map {
                TransactionItem(
                    id = it.id,
                    type = TransactionType.INCOME,
                    title = it.title,
                    dateText = formatDate(it.date),
                    amountText = it.amount.toString(),
                    isImportant = it.isImportant,
                    category = it.category,
                    repetition = it.isRecurring,
                    description = it.description.toString()
                )
            }

            all += notes.map {
                TransactionItem(
                    id = it.id,
                    type = TransactionType.NOTE,
                    title = it.title,
                    dateText = formatDate(it.date),
                    amountText = "",
                    isImportant = it.isImportant,
                    description = it.description
                )
            }

            all += savings.map {
                TransactionItem(
                    id = it.id,
                    type = TransactionType.SAVINGS,
                    title = it.title,
                    dateText = formatDate(it.date),
                    amountText = it.amount.toString(),
                    isImportant = it.isImportant,
                )
            }

            all += debts.map {
                TransactionItem(
                    id = it.id,
                    type = TransactionType.DEBT,
                    title = it.person,
                    dateText = formatDate(it.date),
                    amountText = it.amount.toString(),
                    isImportant = it.isImportant,
                    description = it.title
                )
            }

            all += requests.map {
                TransactionItem(
                    id = it.id,
                    type = TransactionType.REQUEST,
                    title = it.person,
                    dateText = formatDate(it.date),
                    amountText = it.amount.toString(),
                    isImportant = it.isImportant,
                    description = it.title
                )
            }

            all.sortedByDescending { it.dateText }
        }
    }

    // صفحه مشخصات
    override suspend fun getIncomeById(id: Int) = dao.getIncomeById(id)
    override suspend fun getCostById(id: Int) = dao.getCostById(id)
    override suspend fun getNoteById(id: Int) = dao.getNoteById(id)
    override suspend fun getSavingsById(id: Int) = dao.getSavingsById(id)
    override suspend fun getDebtById(id: Int) = dao.getDebtById(id)
    override suspend fun getRequestById(id: Int) = dao.getRequestById(id)

    //حذف
    override suspend fun deleteCost(costs: Costs) = dao.deleteCost(costs)
    override suspend fun deleteIncome(income: Income) = dao.deleteIncome(income)
    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)
    override suspend fun deleteSavings(savings: Savings) = dao.deleteSavings(savings)
    override suspend fun deleteDebt(debt: Debt) = dao.deleteDebt(debt)
    override suspend fun deleteRequest(request: Request) = dao.deleteRequest(request)

    //محاسبات
    override fun getTotalIncome(from: Long, to: Long): Flow<Long?> {
        return dao.getTotalIncome(from, to)
    }
    override fun getTotalCosts(from: Long, to: Long): Flow<Long?> {
        return dao.getTotalCosts(from, to)
    }
    override fun getTotalSavings(from: Long, to: Long): Flow<Long?> {
        return dao.getTotalSavings(from, to)
    }
    override fun getTotalDebt(from: Long, to: Long): Flow<Long?> {
        return dao.getTotalDebt(from, to)
    }
    override fun getTotalRequests(from: Long, to: Long): Flow<Long?> {
        return dao.getTotalRequests(from, to)
    }

    //نمودار
    override fun getMonthlyCosts(from: Long, to: Long): Flow<Long> =
        dao.getCostsByDateRange(from, to)
            .map { list -> list.sumOf { it.amount } }

    override fun getMonthlyIncome(from: Long, to: Long): Flow<Long> =
        dao.getIncomeByDateRange(from, to)
            .map { list -> list.sumOf { it.amount } }

    override fun getMonthlySavings(from: Long, to: Long): Flow<Long> =
        dao.getSavingsByDateRange(from, to)
            .map { list -> list.sumOf { it.amount } }

    //چالش ها
    override fun getAllChallenges(): Flow<List<Challenge>> = dao.getAllChallenge()
    override suspend fun addChallenge(challenge: Challenge) = dao.upsertChallenge(challenge)
}
