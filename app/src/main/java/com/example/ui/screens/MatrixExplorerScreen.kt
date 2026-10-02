package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MatrixExercise
import com.example.data.model.MatrixRepository
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessGreenSuccess
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessTeal

@Composable
fun MatrixExplorerScreen(
    isLargeFontMode: Boolean,
    onSpeak: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    var selectedPosition by remember { mutableIntStateOf(1) } // 1..4
    var selectedResistance by remember { mutableIntStateOf(1) } // 1..4
    var selectedMonthVariant by remember { mutableIntStateOf(1) } // 1..3

    val currentExercise: MatrixExercise = remember(selectedPosition, selectedResistance, selectedMonthVariant) {
        MatrixRepository.getExercise(selectedPosition, selectedResistance, selectedMonthVariant)
    }

    val positionLabels = listOf(
        "P1: 누운/엎드린 (Supine/Prone)",
        "P2: 엎드린/네발 (Prone/Quad)",
        "P3: 네발/앉은 (Quad/Sitting)",
        "P4: 앉은/선 (Sitting/Standing)"
    )

    val resistanceLabels = listOf(
        "R1: 체중/호흡",
        "R2: 중력/경량밴드",
        "R3: 소도구/볼/덤벨",
        "R4: 일상동작(ADL)"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Concept header
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = WellnessBlueContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.GridOn, contentDescription = null, tint = WellnessBluePrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "88웰니스 4×4 매트릭스 운동처방",
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                        color = WellnessBluePrimary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "자유도(Position) 4단계와 부하(Resistance) 4단계의 교차로 완성되는 16개 맞춤 운동 매트릭스입니다. 칸을 터치해보세요.",
                    fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Month Variant Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Pair(1, "1달차 기본형"),
                Pair(2, "2달차 심화형"),
                Pair(3, "3달차 소도구형")
            ).forEach { (v, label) ->
                val isSelected = selectedMonthVariant == v
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) WellnessBluePrimary else MaterialTheme.colorScheme.surface,
                    border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline) else null,
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .clickable { selectedMonthVariant = v }
                        .testTag("matrix_variant_$v")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = label,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4x4 Grid Container
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // X-Axis Header (Resistance)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.width(68.dp), contentAlignment = Alignment.Center) {
                        Text(text = "자유도↓", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = WellnessBluePrimary)
                    }
                    listOf("R1 호흡", "R2 중력", "R3 소도구", "R4 일상").forEach { rTitle ->
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = rTitle, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = WellnessOrangeAccent)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 4 Rows
                for (pos in 1..4) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Row Label (Position)
                        val rowLabel = when (pos) {
                            1 -> "P1 누움"
                            2 -> "P2 네발"
                            3 -> "P3 앉음"
                            else -> "P4 기립"
                        }
                        Box(modifier = Modifier.width(68.dp)) {
                            Text(
                                text = rowLabel,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = WellnessBluePrimary
                            )
                        }

                        // 4 Columns for this row
                        for (res in 1..4) {
                            val isSelected = selectedPosition == pos && selectedResistance == res
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) WellnessBluePrimary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, WellnessOrangeAccent) else null,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .padding(horizontal = 2.dp)
                                    .clickable {
                                        selectedPosition = pos
                                        selectedResistance = res
                                    }
                                    .testTag("matrix_cell_${pos}_$res")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "P$pos×R$res",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                        if (isSelected) {
                                            Text(text = "선택됨", fontSize = 8.sp, color = WellnessOrangeAccent, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selected Cell Exercise Detail
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = WellnessBluePrimary
                    ) {
                        Text(
                            text = "선택: P$selectedPosition × R$selectedResistance (${selectedMonthVariant}달차 버전)",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    IconButton(
                        onClick = {
                            onSpeak("${currentExercise.title}. ${currentExercise.targetBenefit}. ${currentExercise.instructions.joinToString(". ")}")
                        },
                        modifier = Modifier
                            .background(WellnessBlueContainer, CircleShape)
                            .testTag("matrix_voice_speak")
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "음성 안내", tint = WellnessBluePrimary)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = currentExercise.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = if (isLargeFontMode) 20.sp else 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "목표 효과: ${currentExercise.targetBenefit}",
                    fontSize = 13.sp,
                    color = WellnessTeal,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

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
                            Text(text = "자유도 자세", fontSize = 10.sp, color = WellnessBluePrimary, fontWeight = FontWeight.Bold)
                            Text(text = currentExercise.postureLabel, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Surface(
                        color = WellnessOrangeContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "권장 세트", fontSize = 10.sp, color = WellnessOrangeAccent, fontWeight = FontWeight.Bold)
                            Text(text = currentExercise.repetitions, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "동작 순서",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = WellnessBluePrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                currentExercise.instructions.forEachIndexed { i, step ->
                    Row(modifier = Modifier.padding(vertical = 3.dp)) {
                        Text(text = "${i + 1}. ", fontWeight = FontWeight.Bold, color = WellnessBluePrimary, fontSize = 13.sp)
                        Text(
                            text = step,
                            fontSize = if (isLargeFontMode) 15.sp else 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
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
                        Text(text = "⚠️", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = currentExercise.safetyTip,
                            fontSize = if (isLargeFontMode) 13.sp else 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}
