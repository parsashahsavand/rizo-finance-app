package com.example.Parsa.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.Parsa.data.local.entity.Challenge
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    /* -------------------- Costs (CRUD بالا) -------------------- */
    @Upsert
    suspend fun upsertCost(cost: Costs)

    @Delete
    suspend fun deleteCost(cost: Costs)

    // لیست کامل تراکنش‌ها
    @Query("SELECT * FROM costs ORDER BY date DESC")
    fun getAllCosts(): Flow<List<Costs>>

    // دریافت تراکنش‌ها در بازه زمانی (ماهانه، گزارش، نمودار)
    @Query("""
        SELECT * FROM costs
        WHERE date BETWEEN :from AND :to
        ORDER BY date DESC
    """)
    fun getCostsByDateRange(
        from: Long,
        to: Long
    ): Flow<List<Costs>>

    // دریافت یک آیتم خاص (برای صفحه جزئیات)
    @Query("SELECT * FROM costs WHERE id = :id LIMIT 1")
    suspend fun getCostById(id: Int): Costs?

    // جمع کل هزینه‌ها
    @Query("SELECT SUM(amount) FROM costs WHERE date BETWEEN :from AND :to")
    fun getTotalCosts(
        from: Long,
        to: Long
    ): Flow<Long?>

    /* ==================== Income ==================== */
    @Upsert
    suspend fun upsertIncome(income: Income)

    @Delete
    suspend fun deleteIncome(income: Income)

    @Query("SELECT * FROM income ORDER BY date DESC")
    fun getAllIncome(): Flow<List<Income>>

    @Query("""
        SELECT * FROM income
        WHERE date BETWEEN :from AND :to
        ORDER BY date DESC
    """)
    fun getIncomeByDateRange(from: Long, to: Long): Flow<List<Income>>

    @Query("SELECT * FROM income WHERE id = :id LIMIT 1")
    suspend fun getIncomeById(id: Int): Income?

    @Query("SELECT SUM(amount) FROM income WHERE date BETWEEN :from AND :to")
    fun getTotalIncome(
        from: Long,
        to: Long
    ): Flow<Long?>

    /* ==================== Note ==================== */
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE isImportant = 1 ORDER BY date DESC")
    fun getImportantNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Int): Note?


    /* ==================== Savings ==================== */
    @Upsert
    suspend fun upsertSavings(savings: Savings)

    @Delete
    suspend fun deleteSavings(savings: Savings)

    @Query("SELECT * FROM savings ORDER BY date DESC")
    fun getAllSavings(): Flow<List<Savings>>

    @Query("""
        SELECT * FROM savings
        WHERE date BETWEEN :from AND :to
        ORDER BY date DESC
    """)
    fun getSavingsByDateRange(from: Long, to: Long): Flow<List<Savings>>

    @Query("SELECT * FROM savings WHERE id = :id LIMIT 1")
    suspend fun getSavingsById(id: Int): Savings?

    @Query("SELECT SUM(amount) FROM savings WHERE date BETWEEN :from AND :to")
    fun getTotalSavings(
        from: Long,
        to: Long
    ): Flow<Long?>
    /* ==================== Debt ==================== */
    @Upsert
    suspend fun upsertDebt(debt: Debt)

    @Delete
    suspend fun deleteDebt(debt: Debt)

    @Query("SELECT * FROM debt ORDER BY date DESC")
    fun getAllDebts(): Flow<List<Debt>>

    @Query("SELECT * FROM debt WHERE person = :person ORDER BY date DESC")
    fun getDebtsByPerson(person: String): Flow<List<Debt>>

    @Query("SELECT * FROM debt WHERE id = :id LIMIT 1")
    suspend fun getDebtById(id: Int): Debt?

    @Query("SELECT SUM(amount) FROM debt WHERE date BETWEEN :from AND :to")
    fun getTotalDebt(
        from: Long,
        to: Long
    ): Flow<Long?>
    /* ==================== Request ==================== */
    @Upsert
    suspend fun upsertRequest(request: Request)

    @Delete
    suspend fun deleteRequest(request: Request)

    @Query("SELECT * FROM request ORDER BY date DESC")
    fun getAllRequests(): Flow<List<Request>>

    @Query("SELECT * FROM request WHERE person = :person ORDER BY date DESC")
    fun getRequestsByPerson(person: String): Flow<List<Request>>

    @Query("SELECT * FROM request WHERE id = :id LIMIT 1")
    suspend fun getRequestById(id: Int): Request?

    @Query("SELECT SUM(amount) FROM request WHERE date BETWEEN :from AND :to")
    fun getTotalRequests(
        from: Long,
        to: Long
    ): Flow<Long?>

    /* ==================== Challenge ==================== */
    @Query("SELECT * FROM challenge")
    fun getAllChallenge(): Flow<List<Challenge>>

    @Upsert
    suspend fun upsertChallenge(challenge: Challenge)
}