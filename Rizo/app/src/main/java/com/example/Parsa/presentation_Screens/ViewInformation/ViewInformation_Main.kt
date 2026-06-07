package com.example.Parsa.presentation_Screens.ViewInformation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.Parsa.data.local.entity.Costs
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.data.local.entity.Income
import com.example.Parsa.data.local.entity.Note
import com.example.Parsa.data.local.entity.Request
import com.example.Parsa.data.local.entity.Savings
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import com.example.Parsa.presentation_Screens.components.formatDate

@Composable
fun ViewInformation_Main(
    id: Int,
    type: TransactionType,
    viewModel: ViewInformation_MainViewModel = hiltViewModel()
) {
    val data by viewModel.data.collectAsState(initial = null)

    LaunchedEffect(id, type) {
        viewModel.load(id, type)
    }

    if (data == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            text = "جزئیات",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        when (val item = data!!) {
            is Costs -> CostView(item)
            is Income -> IncomeView(item)
            is Note -> NoteView(item)
            is Savings -> SavingsView(item)
            is Debt -> DebtView(item)
            is Request -> RequestView(item)
        }
    }
}


@Composable
fun CostView(cost: Costs) {
    InfoRow("عنوان", cost.title)
    InfoRow("مبلغ", cost.amount.toString())
    InfoRow("تاریخ", formatDate(cost.date))
    InfoRow("دسته‌بندی", cost.category)
    InfoRow("نوع پرداخت", cost.payment_type)
    InfoRow("وضیعت", if (cost.isImportant) "مهم" else "عادی")
    InfoRow("اضافه شده به صفحه اصلی", if (cost.isRecurring) "بله" else "خیر")

    cost.description?.let {
        InfoRow("توضیحات", it)
    }
}


@Composable
fun IncomeView(income: Income) {
    InfoRow("عنوان", income.title)
    InfoRow("مبلغ", income.amount.toString())
    InfoRow("تاریخ", formatDate(income.date))
    InfoRow("دسته‌بندی", income.category)
    InfoRow("وضیعت", if (income.isImportant) "مهم" else "عادی")
    InfoRow("اضافه شده به صفحه اصلی", if (income.isRecurring) "بله" else "خیر")

    income.description?.let {
        InfoRow("توضیحات", it)
    }
}


@Composable
fun NoteView(note: Note) {
    InfoRow("عنوان", note.title)
    InfoRow("توضیحات", note.description)
    InfoRow("تاریخ", formatDate(note.date))
    InfoRow("وضیعت", if (note.isImportant) "مهم" else "عادی")
}


@Composable
fun SavingsView(savings: Savings) {
    InfoRow("عنوان", savings.title)
    InfoRow("مبلغ", savings.amount.toString())
    InfoRow("تاریخ", formatDate(savings.date))
    InfoRow("وضیعت", if (savings.isImportant) "مهم" else "عادی")
}


@Composable
fun DebtView(debt: Debt) {
    InfoRow("عنوان", debt.title)
    InfoRow("شخص", debt.person)
    InfoRow("مبلغ", debt.amount.toString())
    InfoRow("تاریخ", formatDate(debt.date))
    InfoRow("وضیعت", if (debt.isImportant) "مهم" else "عادی")
}


@Composable
fun RequestView(request: Request) {
    InfoRow("عنوان", request.title)
    InfoRow("شخص", request.person)
    InfoRow("مبلغ", request.amount.toString())
    InfoRow("تاریخ", formatDate(request.date))
    InfoRow("وضیعت", if (request.isImportant) "مهم" else "عادی")
}

@Composable
fun InfoRow(title: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(title, fontSize = 13.sp, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        Text(
            if(title == "مبلغ") value.replace(",", "")
                .reversed()
                .chunked(3)
                .joinToString(",")
                .reversed() else value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Divider(Modifier.padding(top = 12.dp))
    }
}

