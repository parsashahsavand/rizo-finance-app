package com.example.Parsa.presentation_Screens.components.List_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizo.finance.R

data class TransactionItem(
    val id: Int,
    val type: TransactionType,
    val title: String,
    val dateText: String,
    val amountText: String,
    val isImportant: Boolean = false,
    val category: String = "",
    val payType: String = "",
    val repetition: Boolean = false,
    val description: String = ""
)

enum class TransactionType { COST, INCOME, NOTE, DEBT, REQUEST, SAVINGS }

@Composable
fun Items(
    items: List<TransactionItem>,
    onItemClick: (TransactionItem) -> Unit,
    onEditClick: (TransactionItem) -> Unit,
    onDeleteClick: (TransactionItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items, key = { item ->
            // ✅ استفاده از ترکیب id و type برای یکتایی موقت
            "${item.id}_${item.type}"
        }) { item ->
            TransactionListItem(
                item = item,
                onClick = { onItemClick(item) },
                onEdit = { onEditClick(item) },
                onDeleteClick = { onDeleteClick(item) }
            )
        }
    }
}

@Composable
fun TransactionListItem(
    item: TransactionItem,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // آیکون نوع تراکنش
                val icon = when (item.type) {
                    TransactionType.COST -> R.drawable.cost
                    TransactionType.INCOME -> R.drawable.income
                    TransactionType.NOTE -> R.drawable.notes
                    TransactionType.DEBT -> R.drawable.debt
                    TransactionType.SAVINGS -> R.drawable.savings
                    TransactionType.REQUEST -> R.drawable.request
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(colors.primary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // عنوان و تاریخ
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (item.type == TransactionType.DEBT || item.type == TransactionType.REQUEST)
                            item.description
                        else
                            item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.dateText,
                        fontSize = 13.sp,
                        color = colors.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // مبلغ
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.widthIn(min = 135.dp)
                ) {
                    Text(
                        text = item.amountText
                            .replace(",", "")
                            .reversed()
                            .chunked(3)
                            .joinToString(",")
                            .reversed(),

                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = when (item.type) {
                            TransactionType.COST -> Color.Red
                            TransactionType.SAVINGS -> Color.Yellow
                            else -> colors.primary
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(0.0001f))

                //  دکمه ویرایش
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "ویرایش",
                        tint = colors.onSurfaceVariant
                    )
                }

                //  دکمه حذف
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .padding(top = if (item.isImportant) 10.dp else 0.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "حذف",
                        tint = Color.Red.copy(alpha = 0.75f)
                    )
                }
            }

            // ستاره مهم بودن
            if (item.isImportant) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "مهم",
                    tint = Color(0xFFFFD54F),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(20.dp)
                )
            }
        }
    }
}


