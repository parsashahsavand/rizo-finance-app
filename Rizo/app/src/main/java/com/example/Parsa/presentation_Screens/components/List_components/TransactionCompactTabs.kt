package com.example.Parsa.presentation_Screens.components.List_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Parsa.presentation_Screens.components.Date_combo_box

@Composable
fun TransactionCompactTabs(
    box : Boolean = true,
    selectedTab: Int,
    onTabChange: (Int) -> Unit,
    selectedItem: String = "",
    onItemSelected: (String) -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val tabs = listOf("همه", "هزینه", "درآمد", "یادداشت", "بدهی", "طلب", "پس انداز")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (box){
            Box(
                modifier = Modifier
                    .width(145.dp)
                    .height(80.dp)
            ) {
                Date_combo_box(
                    label = "",
                    items = listOf("جدیدترین", "قدیمی‌ترین"),
                    size = 1f,
                    selectedItem = selectedItem,
                    onItemSelected = onItemSelected
                )
            }

            Spacer(Modifier.width(10.dp))
        }

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, item ->
                val selected = index == selectedTab

                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (selected) colors.secondaryContainer
                            else colors.surfaceVariant.copy(alpha = 0.4f)
                        )
                        .clickable { onTabChange(index) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = item,
                        color = if (selected) colors.onSecondaryContainer
                        else colors.onSurfaceVariant,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
