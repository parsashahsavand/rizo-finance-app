package com.example.Parsa.presentation_Screens.components

import saman.zamani.persiandate.PersianDate

fun formatDate(millis: Long): String {
    val p = PersianDate(millis)
    return "${p.shYear}/${p.shMonth}/${p.shDay}"
}

fun persianToMillis(year: Int, month: Int, day: Int): Long {
    require(month in 1..12) { "Invalid Persian month: $month" }

    val pd = PersianDate()
    pd.setShYear(year)
    pd.setShMonth(month)
    pd.setShDay(day)
    return pd.time
}

fun persianTextToMillis(date: String): Long {
    val parts = date.split("/")
    if (parts.size != 3) return 0L

    val year = parts[0].toInt()
    val month = parts[1].toInt()
    val day = parts[2].toInt()

    return persianToMillis(year, month, day)
}

