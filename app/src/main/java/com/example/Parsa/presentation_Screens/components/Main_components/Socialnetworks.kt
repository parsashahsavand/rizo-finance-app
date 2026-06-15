package com.example.Parsa.presentation_Screens.components.Main_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizo.finance.R
import com.example.Parsa.presentation_Screens.components.Dialoges.InfoDialog
import kotlinx.coroutines.delay

@Composable
fun Socialnetworks() {
    val colors = MaterialTheme.colorScheme
    var showInfoDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(colors.surface)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // عنوان
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, // مهم: فاصله بین دو گروه
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(
//                        Color.Black.copy(alpha = 0.10f),
//                        shape = RoundedCornerShape(50)
//                    )
                    .padding(vertical = 8.dp, horizontal = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "شبکه‌های اجتماعی ما",
                        color = colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

                IconButton({ showInfoDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "درباره پیج ها",
                        tint = colors.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(Modifier.height(15.dp))

            // اسلایدر شبکه‌های اجتماعی
            AutoScrollSocialSlider()
        }
    }
    if (showInfoDialog) {
        InfoDialog(
            title = "درباره شبکه‌های اجتماعی ما!",
            decryption = "برای اطلاع از بروزرسانی های برنامه و گذاشتن نظرات انتقادات پیشنهادات و ارتباط با ما میتونید شبکه های اجتماعی مارو دنبال کنید علاوه بر اون نکات امورزشی در حوزه امور مالی هم شبکه ها اجتماعی ما هست.",
            onDismiss = { showInfoDialog = false }
        )
    }
}

@Composable
fun AutoScrollSocialSlider() {
    // آیکون و اسم‌ها با هم
    val socialItems = listOf(
        R.drawable.ic_launcher_background to "اینستاگرام",
        R.drawable.ic_launcher_background to "تلگرام",
        R.drawable.ic_launcher_background to "یوتیوب",
        R.drawable.ic_launcher_background to "لینکدین",
        R.drawable.ic_launcher_background to "آپارات",
        R.drawable.ic_launcher_background to "روبیکا"
    )

    val scrollState = rememberLazyListState()

    // حرکت خودکار افقی
    LaunchedEffect(Unit) {
        while (true) {

            if (scrollState.firstVisibleItemIndex ==
                scrollState.layoutInfo.totalItemsCount - 1
            ) {
                scrollState.scrollToItem(0)
            }

            scrollState.animateScrollBy(4f)
            delay(30)
        }
    }

    LazyRow(
        state = scrollState,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        horizontalArrangement = Arrangement.spacedBy(18.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(socialItems) { (icon, name) ->
            SocialItem(icon = icon, name = name)
        }
    }
}

@Composable
fun SocialItem(icon: Int, name: String) {
    val colors = MaterialTheme.colorScheme

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.Black.copy(alpha = 0.10f))
            .padding(6.dp)
            .clickable{}
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = name,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Transparent),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = colors.primary,
            textAlign = TextAlign.Center
        )
    }
}

