package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AnimalCharacter
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessGreenSuccess
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessRedWarning
import com.example.ui.theme.WellnessTeal
import com.example.ui.theme.WellnessTealContainer

@Composable
fun ResultScreen(
    character: AnimalCharacter,
    isLargeFontMode: Boolean,
    onProceedToWorkout: () -> Unit,
    onOpenMatrix: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Result Announcement Header
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = WellnessBlueContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🎉 88웰니스 바디체크 분석 완료", fontWeight = FontWeight.Bold, color = WellnessBluePrimary, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "나의 신체 MBTI 동물 캐릭터가 완성되었습니다!",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Main Animal Character Showcase Card
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Character Emoji Circle
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color(character.badgeColorHex).copy(alpha = 0.15f))
                        .border(2.dp, Color(character.badgeColorHex), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = character.characterEmoji, fontSize = 52.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(character.badgeColorHex)
                ) {
                    Text(
                        text = character.mbtiCode,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = character.name,
                    fontSize = if (isLargeFontMode) 28.sp else 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "“${character.summary}”",
                    fontSize = if (isLargeFontMode) 16.sp else 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = WellnessBluePrimary,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = character.description,
                    fontSize = if (isLargeFontMode) 15.sp else 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 21.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // SFMA 4대 기능 지표 카드
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "📊 신체 기능 균형 지표",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 18.sp else 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                MetricBarItem(label = "유연성 (Mobility)", score = character.mobilityScore, color = WellnessTeal)
                Spacer(modifier = Modifier.height(8.dp))
                MetricBarItem(label = "코어 안정성 (Stability)", score = character.stabilityScore, color = WellnessBluePrimary)
                Spacer(modifier = Modifier.height(8.dp))
                MetricBarItem(label = "밸런스 & 균형 (Balance)", score = character.balanceScore, color = WellnessOrangeAccent)
                Spacer(modifier = Modifier.height(8.dp))
                MetricBarItem(label = "하지 파워 & 근력 (Power)", score = character.powerScore, color = Color(0xFF8B5CF6))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Strengths & Cautions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Strengths
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = WellnessGreenSuccess, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "나의 강점", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    character.strengths.forEach { s ->
                        Text(text = "• $s", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 16.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                    }
                }
            }

            // Caution Points
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = WellnessOrangeAccent, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "주의 사항", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    character.cautionPoints.forEach { c ->
                        Text(text = "• $c", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 16.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4x4 Matrix Prescription Starting Point
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = WellnessBlueContainer.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.GridOn, contentDescription = null, tint = WellnessBluePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "4×4 매트릭스 시작 처방",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = WellnessBluePrimary
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White
                    ) {
                        Text(
                            text = "자유도 × 부하 16단계",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WellnessBluePrimary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "추천 시작점: ${character.matrixStartStage}",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 16.sp else 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "중점 케어: ${character.recommendedFocus}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onOpenMatrix,
                    colors = ButtonDefaults.outlinedButtonColors(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("전체 4×4 매트릭스 16개 운동 살펴보기", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Primary Action: Move to Exercise Program!
        Button(
            onClick = onProceedToWorkout,
            colors = ButtonDefaults.buttonColors(containerColor = WellnessBluePrimary),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .testTag("result_go_to_program_button")
        ) {
            Icon(Icons.Default.DirectionsRun, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "12주 맞춤 운동프로그램 시작하기 (처방 받기)",
                fontSize = if (isLargeFontMode) 18.sp else 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun MetricBarItem(label: String, score: Int, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "$score / 100", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { score / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.15f)
        )
    }
}
