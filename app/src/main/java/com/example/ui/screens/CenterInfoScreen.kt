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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.ui.theme.WellnessBlueContainer
import com.example.ui.theme.WellnessBlueDark
import com.example.ui.theme.WellnessBluePrimary
import com.example.ui.theme.WellnessGreenSuccess
import com.example.ui.theme.WellnessOrangeAccent
import com.example.ui.theme.WellnessOrangeContainer
import com.example.ui.theme.WellnessTeal
import com.example.ui.theme.WellnessTealContainer

@Composable
fun CenterInfoScreen(
    isLargeFontMode: Boolean
) {
    val scrollState = rememberScrollState()
    var isBookingSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Center Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(WellnessBluePrimary)
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = WellnessBluePrimary, modifier = Modifier.size(22.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "88웰니스 오프라인 센터",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isLargeFontMode) 18.sp else 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "온라인 MBTI 진단과 오프라인 1:1 코칭의 결합!",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = if (isLargeFontMode) 22.sp else 19.sp,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "타 피트니스 센터와의 확실한 차별화: 일반 헬스기구 대신 88웰니스 바디체크 기능평가 기반 4×4 매트릭스 전용 장비와 1:1 재활 운동처방사가 상주합니다.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Center 3 Special Differentiation Points
        Text(
            text = "오프라인 센터만의 3대 차별점",
            fontWeight = FontWeight.Bold,
            fontSize = if (isLargeFontMode) 18.sp else 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CenterFeatureItem(
                    emoji = "🩺",
                    title = "신입회원 1:1 88웰니스 바디체크 정밀 모빌리티 측정",
                    desc = "앱에서 체크한 설문 결과를 기반으로 전문 처방사가 관절 가동 각도(ROM)와 보행을 정밀 측정합니다."
                )
                CenterFeatureItem(
                    emoji = "🧱",
                    title = "4×4 매트릭스 전용 소도구 존",
                    desc = "중력 대항 세라밴드, 밸런스 패드, 시니어 미니볼, 안전 손잡이 스텝박스 완비"
                )
                CenterFeatureItem(
                    emoji = "📋",
                    title = "12주 정기 재평가 & 전담 코치 피드백",
                    desc = "4주, 8주차 미니 평가 및 12주 졸업 리포트를 인쇄하여 회원과 가족에게 공유"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Consultation Booking
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = WellnessBlueContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CardMembership, contentDescription = null, tint = WellnessBluePrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "신입회원 오프라인 무료 체험 예약",
                        fontWeight = FontWeight.Bold,
                        fontSize = if (isLargeFontMode) 17.sp else 15.sp,
                        color = WellnessBluePrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "본 설문 결과를 센터 데스크에 보여주시면 1:1 88웰니스 바디체크 정밀 체형 측정 및 매트릭스 운동 1회 체험을 무료로 제공합니다.",
                    fontSize = if (isLargeFontMode) 14.sp else 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (isBookingSuccess) {
                    Surface(
                        color = WellnessGreenSuccess.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = WellnessGreenSuccess)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "예약 신청 완료! 센터 전문 코치가 24시간 내 유선 안내드립니다.",
                                fontWeight = FontWeight.Bold,
                                color = WellnessGreenSuccess,
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    Button(
                        onClick = { isBookingSuccess = true },
                        colors = ButtonDefaults.buttonColors(containerColor = WellnessBluePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("center_booking_button")
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "88웰니스 센터 1:1 방문 예약 신청",
                            fontWeight = FontWeight.Bold,
                            fontSize = if (isLargeFontMode) 16.sp else 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Reference Apps & Expert Recommendations
        Text(
            text = "💡 88웰니스 성공 전략 & 레퍼런스 가이드",
            fontWeight = FontWeight.Bold,
            fontSize = if (isLargeFontMode) 18.sp else 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ReferenceRecommendationItem(
                    target = "1. 미국 실버스니커즈(SilverSneakers) 벤치마킹",
                    note = "낙상 예방(Fall Prevention) 및 의자 운동(Chair-assisted)을 도입하여 운동 초보 시니어의 진입 장벽을 완전히 제거합니다."
                )
                ReferenceRecommendationItem(
                    target = "2. 일본 라이잡(RIZAP) O2O 연계 모델",
                    note = "온라인의 흥미로운 MBTI 동물 테스트를 바이럴 미끼로 활용하고, '내 몸의 진짜 취약점 해결'을 위해 오프라인 센터 방문을 유도하는 최적의 고객 여정(Funnel)을 구축합니다."
                )
                ReferenceRecommendationItem(
                    target = "3. 가족 안심 리포트 공유 기능",
                    note = "시니어 본인뿐만 아니라 자녀들에게 12주 운동 완료 도장 및 통증 개선 지표를 카카오톡으로 발송하여 패밀리 신뢰도를 확보합니다."
                )
            }
        }
    }
}

@Composable
fun CenterFeatureItem(emoji: String, title: String, desc: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
        }
    }
}

@Composable
fun ReferenceRecommendationItem(target: String, note: String) {
    Column {
        Text(text = target, fontWeight = FontWeight.Bold, color = WellnessBluePrimary, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = note, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 16.sp)
    }
}
