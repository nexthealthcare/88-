package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LaborIntensity
import com.example.data.model.PostureStage
import com.example.data.model.SurveyDemographics
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessGreenSuccess
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessRedWarning
import com.example.ui.theme.WellnessTeal
import com.example.ui.viewmodel.SfmaQuestion

@Composable
fun SurveyDemographicsScreen(
    demographics: SurveyDemographics,
    isLargeFontMode: Boolean,
    onUpdateAge: (Int) -> Unit,
    onUpdateGender: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdateLaborIntensity: (LaborIntensity) -> Unit,
    onTogglePainArea: (String) -> Unit,
    onUpdateExerciseFrequency: (String) -> Unit,
    onNext: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = WellnessBlueContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "📋 1단계", fontWeight = FontWeight.Bold, color = WellnessBluePrimary, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "기본 건강 정보 입력 (신체 맞춤 분석용)",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Age
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "1. 나이를 알려주세요",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 18.sp else 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { if (demographics.age > 40) onUpdateAge(demographics.age - 1) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(WellnessBlueContainer, CircleShape)
                            .testTag("demographics_age_minus")
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "나이 감소", tint = WellnessBluePrimary)
                    }

                    Text(
                        text = "${demographics.age} 세",
                        fontSize = if (isLargeFontMode) 32.sp else 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = WellnessBluePrimary,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    IconButton(
                        onClick = { if (demographics.age < 95) onUpdateAge(demographics.age + 1) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(WellnessBlueContainer, CircleShape)
                            .testTag("demographics_age_plus")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "나이 증가", tint = WellnessBluePrimary)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2. Gender
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "2. 성별을 선택해주세요",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 18.sp else 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("여성", "남성").forEach { g ->
                        val isSelected = demographics.gender == g
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) WellnessBluePrimary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .clickable { onUpdateGender(g) }
                                .testTag("demographics_gender_$g")
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = g,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Email
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "3. 결과 분석 리포트 수신 이메일",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 18.sp else 16.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "분석된 MBTI 동물 캐릭터와 12주 운동 플랜을 보내드립니다.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = demographics.email,
                    onValueChange = onUpdateEmail,
                    placeholder = { Text("예: senior88@wellness.com") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = WellnessBluePrimary) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("demographics_email_input"),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4. Labor Intensity
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "4. 직업 및 평소 일상 노동강도",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 18.sp else 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                LaborIntensity.values().forEach { intensity ->
                    val isSelected = demographics.laborIntensity == intensity
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) WellnessBlueContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, WellnessBluePrimary) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable { onUpdateLaborIntensity(intensity) }
                            .testTag("demographics_intensity_${intensity.name}")
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) WellnessBluePrimary else Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSelected) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = intensity.label,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = if (isLargeFontMode) 16.sp else 14.sp,
                                    color = if (isSelected) WellnessBluePrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = intensity.description,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 5. Pain Areas (Multi-select)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "5. 평소 불편하거나 통증이 있는 부위 (중복 선택)",
                    fontWeight = FontWeight.Bold,
                    fontSize = if (isLargeFontMode) 18.sp else 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                val areas = listOf("허리(요추)", "목/어깨", "무릎 관절", "골반/고관절", "발목/발바닥", "통증 없음")
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    areas.chunked(2).forEach { rowAreas ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            rowAreas.forEach { area ->
                                val isSelected = demographics.painAreas.contains(area)
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (isSelected) WellnessOrangeContainer else MaterialTheme.colorScheme.surfaceVariant,
                                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, WellnessOrangeAccent) else null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp)
                                        .clickable { onTogglePainArea(area) }
                                        .testTag("demographics_pain_$area")
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = area,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) WellnessOrangeAccent else MaterialTheme.colorScheme.onSurface,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = WellnessBluePrimary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("demographics_next_button")
        ) {
            Text(
                text = "다음: 88웰니스 바디체크 시작하기",
                fontSize = if (isLargeFontMode) 18.sp else 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun SurveySfmaScreen(
    currentQuestion: SfmaQuestion,
    currentIndex: Int,
    totalQuestions: Int,
    isLargeFontMode: Boolean,
    onAnswer: (hasPain: Boolean, isFunctional: Boolean) -> Unit,
    onPrevious: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Progress Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "88웰니스 바디체크 ${currentIndex + 1} / $totalQuestions",
                fontWeight = FontWeight.Bold,
                color = WellnessBluePrimary,
                fontSize = 14.sp
            )
            Text(
                text = "${((currentIndex + 1).toFloat() / totalQuestions * 100).toInt()}% 완료",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { (currentIndex + 1).toFloat() / totalQuestions },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = WellnessBluePrimary,
            trackColor = WellnessBlueContainer
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Posture Progression Pipeline: SUPINE -> PRONE -> QUADRUPED -> SITTING -> STANDING
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "바디체크 5단계 자세 흐름",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val stages = listOf("누운 자세", "엎드린", "네발기기", "앉은 자세", "서 있는")
                    stages.forEachIndexed { idx, stage ->
                        val isCurrent = idx == currentIndex
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(if (isCurrent) WellnessBluePrimary else if (idx < currentIndex) WellnessTeal else Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${idx + 1}",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stage,
                                fontSize = 9.sp,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                color = if (isCurrent) WellnessBluePrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if (idx < stages.size - 1) {
                            Text(text = "›", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Question Details Card
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = currentQuestion.movement.iconEmoji, fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = currentQuestion.movement.category.krName,
                                fontSize = 12.sp,
                                color = WellnessBluePrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currentQuestion.movement.title,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = if (isLargeFontMode) 20.sp else 17.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    IconButton(
                        onClick = { onSpeak("${currentQuestion.movement.title}. ${currentQuestion.movement.guideDescription}. ${currentQuestion.movement.checkPoint}") },
                        modifier = Modifier
                            .background(WellnessBlueContainer, CircleShape)
                            .testTag("sfma_voice_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "음성 안내",
                            tint = WellnessBluePrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = "자세 경로: ${currentQuestion.movement.targetPostures}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "동작 가이드",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = WellnessBluePrimary
                )
                Text(
                    text = currentQuestion.movement.guideDescription,
                    fontSize = if (isLargeFontMode) 16.sp else 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = WellnessOrangeContainer.copy(alpha = 0.4f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "💡", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = currentQuestion.movement.checkPoint,
                            fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "동작을 직접 해보신 후 상태를 선택해주세요",
            fontWeight = FontWeight.Bold,
            fontSize = if (isLargeFontMode) 16.sp else 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Three Clear Intuitive Option Cards (SFMA: Functional/Non-painful, Dysfunctional/Non-painful, Painful)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Option 1: 정상 가동 & 통증 없음 (FN)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = WellnessGreenSuccess.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, WellnessGreenSuccess),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAnswer(false, true) }
                    .testTag("sfma_option_functional")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(WellnessGreenSuccess),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "통증 없이 부드럽게 잘 됨",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                            color = Color(0xFF065F46)
                        )
                        Text(
                            text = "관절 움직임 정상 (FN: Functional Non-painful)",
                            fontSize = 11.sp,
                            color = Color(0xFF047857)
                        )
                    }
                }
            }

            // Option 2: 통증은 없으나 뻣뻣/제한됨 (DN)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = WellnessOrangeAccent.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, WellnessOrangeAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAnswer(false, false) }
                    .testTag("sfma_option_dysfunctional_no_pain")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(WellnessOrangeAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "△", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "통증은 없으나 움직임이 제한/뻣뻣함",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                            color = Color(0xFF9A3412)
                        )
                        Text(
                            text = "가동성/안정성 개선 필요 (DN: Dysfunctional Non-painful)",
                            fontSize = 11.sp,
                            color = Color(0xFFC2410C)
                        )
                    }
                }
            }

            // Option 3: 동작 시 통증/불편감 있음 (Painful: FP/DP)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = WellnessRedWarning.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, WellnessRedWarning),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAnswer(true, false) }
                    .testTag("sfma_option_painful")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(WellnessRedWarning),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "동작 시 통증이나 찌르는 불편감 있음",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                            color = Color(0xFF991B1B)
                        )
                        Text(
                            text = "통증 보호 및 감압 운동 적용 (Painful Category)",
                            fontSize = 11.sp,
                            color = Color(0xFFDC2626)
                        )
                    }
                }
            }
        }

        if (currentIndex > 0) {
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onPrevious,
                colors = ButtonDefaults.outlinedButtonColors(),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("sfma_previous_button")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("이전 검사 항목으로")
            }
        }
    }
}
