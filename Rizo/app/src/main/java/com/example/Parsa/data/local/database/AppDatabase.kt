package com.example.Parsa.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.Parsa.data.local.dao.AppDao
import com.example.Parsa.data.local.entity.Challenge
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings

@Database(entities = [Costs::class, Income::class, Note::class, Savings::class, Debt::class, Request::class, Challenge::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}