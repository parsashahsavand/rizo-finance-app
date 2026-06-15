package com.example.Parsa.data.local.entity

import com.example.Parsa.presentation_Screens.components.List_components.TransactionItem


fun TransactionItem.toCost(): Costs {
    return Costs(
        id = this.id,
        title = this.title,
        amount = this.amountText.toLong(),
        category = this.category,
        date = this.dateText.toLongOrNull() ?: System.currentTimeMillis(),
        payment_type = this.payType,
        isImportant = this.isImportant,
        isRecurring = false,
        description = this.description
    )
}

fun TransactionItem.toIncome(): Income {
    return Income(
        id = this.id,
        title = this.title,
        amount = this.amountText.toLong(),
        category = this.category,
        date = this.dateText.toLongOrNull() ?: System.currentTimeMillis(),
        isImportant = this.isImportant,
        isRecurring = false,
        description = this.description
    )
}

fun TransactionItem.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        date = this.dateText.toLongOrNull() ?: System.currentTimeMillis(),
        isImportant = this.isImportant,
        description = this.description
    )
}

fun TransactionItem.toSavings(): Savings {
    return Savings(
        id = this.id,
        title = this.title,
        amount = this.amountText.toLong(),
        date = this.dateText.toLongOrNull() ?: System.currentTimeMillis(),
        isImportant = this.isImportant,
    )
}

fun TransactionItem.toDebt(): Debt {
    return Debt(
        id = this.id,
        title = this.title,
        person = this.description,
        amount = this.amountText.toLong(),
        date = this.dateText.toLongOrNull() ?: System.currentTimeMillis(),
        isImportant = this.isImportant,
    )
}

fun TransactionItem.toRequest(): Request {
    return Request(
        id = this.id,
        title = this.title,
        person = this.description,
        amount = this.amountText.toLong(),
        date = this.dateText.toLongOrNull() ?: System.currentTimeMillis(),
        isImportant = this.isImportant,
    )
}