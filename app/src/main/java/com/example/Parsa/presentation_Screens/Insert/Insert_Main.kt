package com.example.Parsa.presentation_Screens.Insert

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dotlottie.dlplayer.Mode
import com.example.Parsa.ui.theme.insertGradient
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.rizo.finance.R
import com.example.Parsa.presentation_Screens.aa_Navigation.Add_data
import com.example.Parsa.presentation_Screens.components.rawToJson


@Composable
fun Insert_Main(navController: NavHostController) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 65.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InsertItem(
                title = "ثبت درآمد",
                icon = rawToJson(R.raw.income),
                item = "income",
                modifier = Modifier.weight(0.8f),
                navController = navController
            )

            InsertItem(
                title = "ثبت هزینه",
                icon = rawToJson(R.raw.cost),
                item = "cost",
                modifier = Modifier.weight(0.8f),
                navController = navController
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InsertItem(
                title = "ثبت پس انداز",
                icon = rawToJson(R.raw.save_money),
                item = "savings",
                modifier = Modifier.weight(0.8f),
                navController = navController
            )
            InsertItem(
                title = "ثبت یادداشت ها",
                icon = rawToJson(R.raw.note),
                item = "note",
                modifier = Modifier.weight(0.8f),
                navController = navController
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InsertItem(
                title = "ثبت طلب ها",
                icon = rawToJson(R.raw.request),
                item = "request",
                modifier = Modifier.weight(0.8f),
                navController = navController
            )
            InsertItem(
                title = "ثبت بدهی ها",
                icon = rawToJson(R.raw.debt),
                item = "debt",
                modifier = Modifier.weight(0.8f),
                navController = navController
            )
        }
    }
}

@Composable
fun InsertItem(
    title: String,
    icon: String,
    item: String,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val insertGradient = insertGradient(item)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .border(6.dp, insertGradient, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(insertGradient)
            .clickable { navController.navigate(Add_data(item)) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Json(icon),
                autoplay = true,
                loop = true,
                speed = 0.7f,
                useFrameInterpolation = false,
                playMode = Mode.FORWARD,
                modifier = Modifier.size(113.dp)
            )

            Spacer(Modifier.height(12.dp)) 

            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
