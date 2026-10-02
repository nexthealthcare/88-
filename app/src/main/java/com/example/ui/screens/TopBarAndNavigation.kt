package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.viewmodel.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WellnessTopBar(
    title: String,
    canNavigateBack: Boolean,
    isLargeFontMode: Boolean,
    onBackClick: () -> Unit,
    onToggleLargeFont: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(WellnessBluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "88",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 17.sp
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = if (isLargeFontMode) 20.sp else 17.sp
                    )
                    Text(
                        text = "88웰니스 바디체크 맞춤처방",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp
                    )
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기"
                    )
                }
            }
        },
        actions = {
            // Senior accessibility: Large Font Toggle button
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isLargeFontMode) WellnessBluePrimary else WellnessBlueContainer,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onToggleLargeFont() }
                    .testTag("toggle_large_font_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TextFields,
                        contentDescription = "글자 크기",
                        tint = if (isLargeFontMode) Color.White else WellnessBluePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isLargeFontMode) "큰글씨 ON" else "글자 확대",
                        color = if (isLargeFontMode) Color.White else WellnessBluePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun WellnessBottomNavigation(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { onNavigate(AppScreen.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "홈") },
            label = { Text("홈", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WellnessBluePrimary,
                selectedTextColor = WellnessBluePrimary,
                indicatorColor = WellnessBlueContainer
            ),
            modifier = Modifier.testTag("nav_home")
        )
        NavigationBarItem(
            selected = currentScreen == AppScreen.ROADMAP || currentScreen == AppScreen.WORKOUT_PLAYER,
            onClick = { onNavigate(AppScreen.ROADMAP) },
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "12주 처방") },
            label = { Text("12주 처방", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WellnessBluePrimary,
                selectedTextColor = WellnessBluePrimary,
                indicatorColor = WellnessBlueContainer
            ),
            modifier = Modifier.testTag("nav_roadmap")
        )
        NavigationBarItem(
            selected = currentScreen == AppScreen.MATRIX_EXPLORER,
            onClick = { onNavigate(AppScreen.MATRIX_EXPLORER) },
            icon = { Icon(Icons.Default.GridOn, contentDescription = "4x4 매트릭스") },
            label = { Text("4×4 매트릭스", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WellnessBluePrimary,
                selectedTextColor = WellnessBluePrimary,
                indicatorColor = WellnessBlueContainer
            ),
            modifier = Modifier.testTag("nav_matrix")
        )
        NavigationBarItem(
            selected = currentScreen == AppScreen.CENTER_INFO,
            onClick = { onNavigate(AppScreen.CENTER_INFO) },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "88센터") },
            label = { Text("88센터", fontWeight = FontWeight.SemiBold, fontSize = 12.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = WellnessBluePrimary,
                selectedTextColor = WellnessBluePrimary,
                indicatorColor = WellnessBlueContainer
            ),
            modifier = Modifier.testTag("nav_center")
        )
    }
}
