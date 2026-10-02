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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.WorkoutLogEntity
import com.example.data.model.ProgramRoadmap
import com.example.data.model.WeekProgram
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessGreenSuccess
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessTeal
import com.example.ui.theme.WellnessTealContainer

@Composable
fun RoadmapScreen(
    workoutLogs: List<WorkoutLogEntity>,
    isLargeFontMode: Boolean,
    onStartWorkoutForWeek: (Int) -> Unit,
    onStartEvaluation: () -> Unit
) {
    var selectedMonth by remember { mutableIntStateOf(1) } // 1, 2, 3

    val filteredWeeks = ProgramRoadmap.weeks.filter { it.monthNumber == selectedMonth }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Month Tabs Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
                ScrollableTabRow(
                    selectedTabIndex = selectedMonth - 1,
                    edgePadding = 16.dp,
                    containerColor = Color.Transparent,
                    indicator = {},
                    divider = {}
                ) {
                    val months = listOf(
                        "1달차 (1~4주) : 자유도 적응",
                        "2달차 (5~8주) : 운동 다양화",
                        "3달차 (9~12주) : 소도구 & 일상전이"
                    )
                    months.forEachIndexed { index, title ->
                        val isSelected = selectedMonth == index + 1
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) WellnessBluePrimary else WellnessBlueContainer,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { selectedMonth = index + 1 }
                                .testTag("roadmap_month_tab_${index + 1}")
                        ) {
                            Text(
                                text = title,
                                color = if (isSelected) Color.White else WellnessBluePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = if (isLargeFontMode) 14.sp else 13.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                // Month Concept Description Banner
                val monthConcept = when (selectedMonth) {
                    1 -> "💡 1달차 원칙: 척추 감압 누운 자세부터 서 있는 자세까지 주차별로 자유도를 점진적으로 올리며 기초 체중 지지 코어를 학습합니다."
                    2 -> "💡 2달차 원칙: 1달차와 동일한 자유도 흐름(바닥→기립)을 반복하되, 동작의 종류를 다양화하여 관절 적응력과 협응을 높입니다."
                    else -> "💡 3달차 원칙: 소도구(밴드, 미니볼) 저항을 활용하여 계단 오르기, 짐 들기 등 실생활 동작(ADL)으로 전이시킵니다."
                }
                Surface(
                    color = WellnessTealContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = monthConcept,
                        fontSize = if (isLargeFontMode) 13.sp else 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(10.dp),
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Weeks List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(filteredWeeks) { week ->
                val isCompleted = workoutLogs.any { it.weekNumber == week.weekNumber }

                WeekProgramCard(
                    week = week,
                    isCompleted = isCompleted,
                    isLargeFontMode = isLargeFontMode,
                    onStartWorkout = { onStartWorkoutForWeek(week.weekNumber) },
                    onStartEvaluation = onStartEvaluation
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun WeekProgramCard(
    week: WeekProgram,
    isCompleted: Boolean,
    isLargeFontMode: Boolean,
    onStartWorkout: () -> Unit,
    onStartEvaluation: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Week tag + Completed badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = WellnessBluePrimary,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "${week.weekNumber}주차",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                if (isCompleted) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = WellnessGreenSuccess, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "완료됨", color = WellnessGreenSuccess, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "진행 가능",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = week.title,
                fontWeight = FontWeight.Bold,
                fontSize = if (isLargeFontMode) 18.sp else 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = week.themeDescription,
                fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Posture & Resistance Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = WellnessBlueContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "자유도(Position)", fontSize = 10.sp, color = WellnessBluePrimary, fontWeight = FontWeight.Bold)
                        Text(text = week.positionFocus, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Surface(
                    color = WellnessOrangeContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "부하(Resistance)", fontSize = 10.sp, color = WellnessOrangeAccent, fontWeight = FontWeight.Bold)
                        Text(text = week.resistanceFocus, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Evaluation Highlight Badge if applicable
            if (week.isEvaluationWeek && week.evaluationTitle != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    color = WellnessOrangeAccent.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WellnessOrangeAccent),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Default.Stars, contentDescription = null, tint = WellnessOrangeAccent, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = week.evaluationTitle,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = WellnessOrangeAccent
                            )
                        }
                        Button(
                            onClick = onStartEvaluation,
                            colors = ButtonDefaults.buttonColors(containerColor = WellnessOrangeAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("평가하기", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action: Start Workout Session
            Button(
                onClick = onStartWorkout,
                colors = ButtonDefaults.buttonColors(containerColor = WellnessBluePrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("start_workout_week_${week.weekNumber}")
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${week.weekNumber}주차 맞춤 운동 재생하기 (음성 코칭)",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 15.sp else 14.sp
                )
            }
        }
    }
}
