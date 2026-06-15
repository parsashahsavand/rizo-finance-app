package com.example.Parsa.presentation_Screens.components.Main_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.Parsa.presentation_Screens.Main_Screen.MainViewModel
import com.example.Parsa.presentation_Screens.components.persianToMillis
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import saman.zamani.persiandate.PersianDate

@Composable
fun BarChart(
    viewModel: MainViewModel,
    selectedYear: Int
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    val months = listOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر",
        "مرداد", "شهریور", "مهر", "آبان",
        "آذر", "دی", "بهمن", "اسفند"
    )

    val yearlyDataState =
        remember { mutableStateOf<List<Triple<Long, Long, Long>>?>(null) }

    LaunchedEffect(selectedYear) {
        viewModel.loadYearlyData(selectedYear).collect {
            yearlyDataState.value = it
        }
    }

    val barChart = remember {
        BarChart(context).apply {
            description.isEnabled = false
            axisRight.isEnabled = false
            setDrawGridBackground(false)

            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.granularity = 1f
            xAxis.textColor = colors.onSurface.toArgb()

            axisLeft.setDrawGridLines(true)
            axisLeft.textColor = colors.onSurface.toArgb()

            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.textColor = colors.onSurface.toArgb()
        }
    }

    LaunchedEffect(yearlyDataState.value) {
        val data = yearlyDataState.value ?: return@LaunchedEffect

        val allZero = data.all { it.first == 0L && it.second == 0L && it.third == 0L }

        if (allZero) {
            barChart.clear()
            barChart.setNoDataText("اطلاعاتی برای این سال ثبت نشده است")
            barChart.invalidate()
            return@LaunchedEffect
        }

        val expenseEntries = mutableListOf<BarEntry>()
        val savingEntries = mutableListOf<BarEntry>()
        val incomeEntries = mutableListOf<BarEntry>()

        data.forEachIndexed { index, triple ->
            expenseEntries.add(BarEntry(index.toFloat(), triple.first.toFloat()))
            savingEntries.add(BarEntry(index.toFloat(), triple.second.toFloat()))
            incomeEntries.add(BarEntry(index.toFloat(), triple.third.toFloat()))
        }

        val expenseSet = BarDataSet(expenseEntries, "هزینه‌ها").apply {
            color = Color(0xFFE53935).toArgb()
            valueTextColor = colors.onSurface.toArgb()
        }

        val savingSet = BarDataSet(savingEntries, "پس‌انداز").apply {
            color = Color(0xFFFFEB3B).toArgb()
            valueTextColor = colors.onSurface.toArgb()
        }

        val incomeSet = BarDataSet(incomeEntries, "درآمد").apply {
            color = Color(0xFF49E51E).toArgb()
            valueTextColor = colors.onSurface.toArgb()
        }

        val barData = BarData(expenseSet, savingSet, incomeSet)

        val groupSpace = 0.34f
        val barSpace = 0.03f
        val barWidth = 0.18f

        barData.barWidth = barWidth
        barChart.data = barData

        barChart.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(months)
            labelCount = 12
            granularity = 1f

            // خطت برای جدول
            setDrawGridLines(false)
            setCenterAxisLabels(true)

            textSize = 10f
            labelRotationAngle = -45f
        }

        barChart.apply {
            setPinchZoom(true)      // زوم با دو انگشت
            setScaleEnabled(true)   // زوم فعال
            isDragEnabled = true    // اسکرول افقی
            setVisibleXRangeMaximum(8f) // همزمان ۶ ماه دیده شود
        }

        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.invalidate()
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(14.dp)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .padding(16.dp),
            factory = { barChart }
        )
    }
}

@Composable
fun PieChart1(
    viewModel: MainViewModel,
    selectedYear: Int,
    selectedMonth: Int,
    monthName: String
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme

    val (fromMillis, toMillis) = remember(selectedYear, selectedMonth) {
        getMonthRangeMillis(selectedYear, selectedMonth)
    }

    val pieDataState = remember { mutableStateOf<Triple<Long, Long, Long>?>(null) }

    LaunchedEffect(fromMillis, toMillis) {
        viewModel.loadMonthlyData(fromMillis, toMillis)
            .collect { triple ->
                pieDataState.value = triple
            }
    }

    val pieChart = remember {
        PieChart(context).apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setDrawEntryLabels(false)
            setDrawHoleEnabled(true)
            holeRadius = 55f
            setHoleColor(android.graphics.Color.TRANSPARENT)
            setTransparentCircleAlpha(0)

            legend.isEnabled = true
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.setDrawInside(false)

            setDrawCenterText(true)
        }
    }

    LaunchedEffect(pieDataState.value) {
        pieDataState.value?.let { (cost, saving, income) ->
            if (cost == 0L && saving == 0L && income == 0L) {
                // داده‌ای موجود نیست → فقط centerText پیام بده
                pieChart.data = null
                pieChart.centerText = "اطلاعاتی ذخیره نشده است"
                pieChart.invalidate()
            } else {
                val entries = listOf(
                    PieEntry(if(cost > 0) cost.toFloat() else 0.01f, "هزینه‌ها"),
                    PieEntry(if(saving > 0) saving.toFloat() else 0.01f, "پس‌انداز"),
                    PieEntry(if(income > 0) income.toFloat() else 0.01f, "درآمد")
                )

                val dataSet = PieDataSet(entries, "").apply {
                    setColors(
                        listOf(
                            Color(0xFFE53935).toArgb(),
                            Color(0xFFFFEB3B).toArgb(),
                            Color(0xFF49E51E).toArgb()
                        )
                    )
                    valueTextSize = 12f
                    sliceSpace = 0f
                    selectionShift = 6f
                    valueTextColor = Color.Black.toArgb()
                }

                pieChart.data = PieData(dataSet).apply {
                    setValueFormatter(PercentFormatter(pieChart))
                    setValueTextColor(Color.Black.toArgb())
                    setValueTextSize(12f)
                }

                pieChart.centerText = monthName
                pieChart.highlightValues(null)
                pieChart.invalidate()
            }
        }
    }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.elevatedCardElevation(12.dp)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            factory = { pieChart }
        )
    }
}


fun persianToMillisEndOfDay(year: Int, month: Int, day: Int): Long {
    val pd = PersianDate()
    pd.setShYear(year)
    pd.setShMonth(month)
    pd.setShDay(day)
    pd.setHour(23)
    pd.setMinute(59)
    pd.setSecond(59)
    return pd.time
}

fun getMonthRangeMillis(year: Int, month: Int): Pair<Long, Long> {
    val from = persianToMillis(year, month, 1)
    val lastDay = when (month) {
        1,2,3,4,5,6 -> 31
        7,8,9,10,11 -> 30
        12 -> 29
        else -> 30
    }
    val to = persianToMillisEndOfDay(year, month, lastDay)
    return from to to
}