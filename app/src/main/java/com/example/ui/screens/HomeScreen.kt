package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AssessmentEntity
import com.example.data.local.UserEntity
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBlueDark
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessTeal
import com.example.ui.theme.WellnessTealContainer
import com.example.ui.viewmodel.AppScreen

@Composable
fun HomeScreen(
    currentUser: UserEntity?,
    latestAssessment: AssessmentEntity?,
    isLargeFontMode: Boolean,
    onStartSurvey: () -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp)
    ) {
        // --- Hero Card Banner ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            WellnessBluePrimary,
                            WellnessBlueDark
                        )
                    )
                )
                .padding(22.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = "액티브 시니어 특화 헬스케어",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }

                    if (currentUser != null) {
                        Text(
                            text = "${currentUser.name} 회원님 환영합니다",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "오래도록 건강하게!\n88웰니스 바디체크 1:1 맞춤 운동처방",
                    color = Color.White,
                    fontSize = if (isLargeFontMode) 26.sp else 23.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = if (isLargeFontMode) 34.sp else 30.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "통증과 불편함은 덜어내고, 4×4 매트릭스로 일상 활력을 안전하게 되찾아드립니다.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = if (isLargeFontMode) 15.sp else 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onStartSurvey,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = WellnessBluePrimary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .testTag("home_start_survey_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (latestAssessment != null) "88웰니스 바디체크 다시하기" else "3분 88웰니스 바디체크 & MBTI 동물 찾기",
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isLargeFontMode) 17.sp else 15.sp
                    )
                }
            }
        }

        // --- Current Assessment Result Snapshot (if available) ---
        if (latestAssessment != null) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(WellnessOrangeContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "🐾", fontSize = 22.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "나의 신체 MBTI 유형",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = latestAssessment.characterName,
                                    fontSize = if (isLargeFontMode) 20.sp else 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WellnessBluePrimary
                                )
                            }
                        }

                        Surface(
                            color = WellnessBlueContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = latestAssessment.notes,
                                fontSize = 11.sp,
                                color = WellnessBluePrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ScoreBadge(label = "유연성", score = latestAssessment.mobilityScore, color = WellnessTeal)
                        ScoreBadge(label = "안정성", score = latestAssessment.stabilityScore, color = WellnessBluePrimary)
                        ScoreBadge(label = "밸런스", score = latestAssessment.balanceScore, color = WellnessOrangeAccent)
                        ScoreBadge(label = "파워", score = latestAssessment.powerScore, color = Color(0xFF8B5CF6))
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { onNavigate(AppScreen.ROADMAP) },
                        colors = ButtonDefaults.buttonColors(containerColor = WellnessBluePrimary),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("home_go_to_roadmap_button")
                    ) {
                        Icon(Icons.Default.DirectionsRun, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "12주 맞춤 운동프로그램 입장하기",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isLargeFontMode) 16.sp else 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Core Highlights Grid ---
        Text(
            text = "88웰니스만의 3대 차별점",
            fontSize = if (isLargeFontMode) 20.sp else 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FeatureHighlightCard(
                title = "88웰니스 바디체크",
                subtitle = "FLEXION부터 SQUAT까지 간결한 5자세 평가",
                emoji = "📐",
                modifier = Modifier.weight(1f),
                onClick = onStartSurvey
            )
            FeatureHighlightCard(
                title = "4×4 매트릭스",
                subtitle = "자유도(4단계) × 부하(4단계) 맞춤 처방",
                emoji = "🧩",
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(AppScreen.MATRIX_EXPLORER) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FeatureHighlightCard(
                title = "12주 로드맵",
                subtitle = "4주·8주 미니평가 & 12주 최종 재평가",
                emoji = "🗓️",
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(AppScreen.ROADMAP) }
            )
            FeatureHighlightCard(
                title = "오프라인 88센터",
                subtitle = "신입회원 1:1 측정 & 전문가 코칭 연동",
                emoji = "🏥",
                modifier = Modifier.weight(1f),
                onClick = { onNavigate(AppScreen.CENTER_INFO) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Web Page Entry Card (HTTP / HTTPS support)
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = WellnessBlueContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigate(AppScreen.WEB_VIEW) }
                .testTag("home_go_to_web_button")
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(WellnessBluePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🌐", fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "88WORKOUT 웹페이지",
                                fontWeight = FontWeight.Bold,
                                fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                                color = WellnessBluePrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.White
                            ) {
                                Text(
                                    text = "HTTP 지원",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WellnessBluePrimary,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "클라우드플레어 & HTTP 웹 바디체크 바로 열기",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = WellnessBluePrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // --- Active Senior Safety Banner ---
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = WellnessTealContainer.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(WellnessTeal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "시니어 안심 운동 원칙",
                        fontWeight = FontWeight.Bold,
                        color = WellnessTeal,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "동작 중 날카로운 통증이나 어지럼증이 있으면 즉시 동작을 멈추고 편안하게 호흡하세요.",
                        fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ScoreBadge(label: String, score: Int, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text = label, fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold)
        Text(text = "$score 점", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
fun FeatureHighlightCard(
    title: String,
    subtitle: String,
    emoji: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = emoji, fontSize = 26.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 15.sp
            )
        }
    }
}
