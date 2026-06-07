package com.example.Parsa.presentation_Screens.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TutorialScreen(onComplete: () -> Unit) {
   // val colors = listOf(Color(0xFF1A2A6C), Color(0xFFB21F1F), Color(0xFFFDBB2D)) // گرادیانت سلطنتی

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            // هدر با کارت شیشه‌ای
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "✨ راهنمای سریع ✨",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "مدیریت مالی، ساده تر از همیشه.",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // کارت توضیحات اصلی
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "💡 چرا اینجا هستی؟",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C5364)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "پول هم مثل بقیه چیزها نیاز به برنامه دارد—اما لازم نیست سخت باشد. اینجا می‌تونی قدم‌به‌قدم وضعیت مالی‌ات رو روشن ببینی و کنترل بیشتری روی پولت داشته باشی.",
                        fontSize = 15.sp,
                        color = Color(0xFF333333),
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // بخش چالش‌ها با کارت‌های رنگی
            Text(
                text = "📋 قدم‌های اولیه",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            ListItemModern(
                text = "ℹ️" + " راهنما - هر بخش رو متوجه نشدی یا میخواستی بشتر بدونی رو علامت تعجب کلیک کن",
                color = Color(0xFF056508)
            )
            ListItemModern(
                text = "📊 صفحه اصلی - نمودار و چالش‌های روزانه",
                color = Color(0xFFFF6B6B)
            )
            ListItemModern(
                text = "➕ ثبت تراکنش‌های مالی جدید",
                color = Color(0xFF4ECDC4)
            )
            ListItemModern(
                text = "📋 لیست تراکنش‌ها با قابلیت فیلتر",
                color = Color(0xFF45B7D1)
            )
            ListItemModern(
                text = "⭐ کامل کردن ۴ چالش = ۱ ستاره افتخار",
                color = Color(0xFFFFE66D)
            )

            Spacer(Modifier.height(20.dp))

            // باکس تشویقی
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFD700).copy(alpha = 0.2f),
                border = BorderStroke(1.dp, Color(0xFFFFD700))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("⭐", fontSize = 32.sp)
                    Text(
                        text = "هر ۴ چالش = یک ستاره!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                    Text(
                        text = "ستاره‌ها نشون دهنده پشت کار و هوشمندی شماست",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // دکمه پایانی
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "🚀 شروع کن!",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "جیباتون پر پول 💰",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun ListItemModern(
    text: String,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f)
            )
        }
    }
}