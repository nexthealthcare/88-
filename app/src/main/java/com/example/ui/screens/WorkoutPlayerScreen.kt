package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.ProgramRoadmap
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessGreenSuccess
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessTeal

@Composable
fun WorkoutPlayerScreen(
    selectedWeekNumber: Int,
    activeExerciseIndex: Int,
    timerSeconds: Int,
    isTimerRunning: Boolean,
    isLargeFontMode: Boolean,
    onToggleTimer: () -> Unit,
    onNextOrFinish: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val week = ProgramRoadmap.weeks.firstOrNull { it.weekNumber == selectedWeekNumber }
        ?: ProgramRoadmap.weeks.first()
    val totalExercises = week.exercises.size
    val currentExercise = week.exercises.getOrNull(activeExerciseIndex) ?: week.exercises.first()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Week & Progress Header
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
                    text = "${week.weekNumber}주차 루틴",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }

            Text(
                text = "동작 ${activeExerciseIndex + 1} / $totalExercises",
                fontWeight = FontWeight.Bold,
                color = WellnessBluePrimary,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Title & Posture
        Text(
            text = currentExercise.title,
            fontWeight = FontWeight.ExtraBold,
            fontSize = if (isLargeFontMode) 22.sp else 19.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${currentExercise.postureLabel} • ${currentExercise.resistanceLabel}",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Big Circular Timer for Active Senior Eyesight
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp)
        ) {
            val totalDuration = currentExercise.durationSeconds.toFloat()
            val progress = if (totalDuration > 0) (timerSeconds / totalDuration).coerceIn(0f, 1f) else 0f

            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(190.dp),
                color = if (timerSeconds <= 5) WellnessOrangeAccent else WellnessBluePrimary,
                strokeWidth = 12.dp,
                trackColor = WellnessBlueContainer
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (timerSeconds > 0) "${timerSeconds}초" else "완료!",
                    fontSize = if (timerSeconds > 0) 42.sp else 32.sp,
                    fontWeight = FontWeight.Black,
                    color = if (timerSeconds <= 5 && timerSeconds > 0) WellnessOrangeAccent else WellnessBluePrimary
                )
                Text(
                    text = currentExercise.repetitions,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Timer Control Buttons (Play/Pause, Next)
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Play / Pause
            Button(
                onClick = onToggleTimer,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTimerRunning) WellnessOrangeAccent else WellnessBluePrimary
                ),
                shape = CircleShape,
                modifier = Modifier
                    .size(64.dp)
                    .testTag("workout_toggle_timer")
            ) {
                Icon(
                    imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isTimerRunning) "일시정지" else "재생",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Next / Finish Button
            Button(
                onClick = onNextOrFinish,
                colors = ButtonDefaults.buttonColors(containerColor = WellnessTeal),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .height(60.dp)
                    .testTag("workout_next_button")
            ) {
                Icon(
                    imageVector = if (activeExerciseIndex < totalExercises - 1) Icons.Default.SkipNext else Icons.Default.Check,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (activeExerciseIndex < totalExercises - 1) "다음 동작으로" else "오늘 운동 완료!",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 17.sp else 15.sp
                )
            }

            // Audio TTS repeat
            IconButton(
                onClick = { onSpeak("${currentExercise.title}. ${currentExercise.instructions.joinToString(". ")}") },
                modifier = Modifier
                    .size(50.dp)
                    .background(WellnessBlueContainer, CircleShape)
                    .testTag("workout_repeat_tts")
            ) {
                Icon(Icons.Default.VolumeUp, contentDescription = "음성 다시 듣기", tint = WellnessBluePrimary)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Step by step Instructions Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "📋 동작 가이드",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                    color = WellnessBluePrimary
                )
                Spacer(modifier = Modifier.height(10.dp))

                currentExercise.instructions.forEachIndexed { i, step ->
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(text = "${i + 1}. ", fontWeight = FontWeight.Bold, color = WellnessBluePrimary, fontSize = 14.sp)
                        Text(
                            text = step,
                            fontSize = if (isLargeFontMode) 16.sp else 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = WellnessOrangeContainer.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "⚠️", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = currentExercise.safetyTip,
                            fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }
    }
}
