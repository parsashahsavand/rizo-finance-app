package com.example.Parsa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "costs")
data class Costs(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Long,
    val category: String,
    val date: Long,
    val payment_type: String,
    val isImportant: Boolean = false,
    val isRecurring: Boolean = false,
    val description: String? = null
)

@Entity(tableName = "income")
data class Income(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Long,
    val category: String,
    val date: Long,
    val isImportant: Boolean = false,
    val isRecurring: Boolean = false,
    val description: String? = null
)

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: Long,
    val isImportant: Boolean = false,
    val description: String
)

@Entity(tableName = "savings")
data class Savings(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Long,
    val date: Long,
    val isImportant: Boolean = false,
)

@Entity(tableName = "debt")
data class Debt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val person: String,
    val title: String,
    val amount: Long,
    val date: Long,
    val isImportant: Boolean = false,
)

@Entity(tableName = "request")
data class Request(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val person: String,
    val title: String,
    val amount: Long,
    val date: Long,
    val isImportant: Boolean = false,
)

@Entity(tableName = "challenge")
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val textChallenge1: String,
    val textChallenge2: String,
    val textChallenge3: String,
    val textChallenge4: String,
    val checkChallenge1: Boolean,
    val checkChallenge2: Boolean,
    val checkChallenge3: Boolean,
    val checkChallenge4: Boolean,
)