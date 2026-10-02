package com.example.data.model

data class SurveyDemographics(
    val age: Int = 65,
    val gender: String = "여성",
    val email: String = "",
    val laborIntensity: LaborIntensity = LaborIntensity.MODERATE,
    val painAreas: Set<String> = emptySet(),
    val exerciseFrequency: String = "주 2-3회"
)

enum class LaborIntensity(val label: String, val description: String) {
    SEDENTARY("좌식 위주 (낮음)", "주로 앉아서 생활하거나 정적인 활동 위주"),
    LIGHT("가벼운 활동 (보통 이하)", "가사 노동, 가벼운 산책 등 약간의 신체 움직임"),
    MODERATE("보통 활동 (적정)", "규칙적인 보행, 정원 가꾸기, 활발한 일상생활"),
    VIGOROUS("육체 노동 (높음)", "무거운 물건 들기, 농사, 장시간 서서 일하는 육체활동")
}

data class SfmaMovement(
    val id: String,
    val title: String,
    val category: MovementCategory,
    val targetPostures: String, // "누운 자세 (SUPINE) -> 서 있는 자세 (STANDING)"
    val guideDescription: String,
    val checkPoint: String,
    val iconEmoji: String
)

enum class MovementCategory(val krName: String) {
    FLEXION("굴곡 (Flexion)"),
    EXTENSION("신전 (Extension)"),
    ROTATION("회전 (Rotation)"),
    SINGLE_LEG_STANCE("한 발 서기 (Single Leg Stance)"),
    SQUAT("스쿼트 (Squat)")
}

data class MovementAssessmentResult(
    val movementId: String,
    val posture: PostureStage,
    val hasPain: Boolean,
    val isFunctional: Boolean // true = 정상 가동, false = 제한됨/기능부전
)

enum class PostureStage(val krName: String, val level: Int) {
    SUPINE("누운 자세 (Supine)", 1),
    PRONE("엎드린 자세 (Prone)", 2),
    QUADRUPED("네발기기 자세 (Quadruped)", 3),
    SITTING("앉은 자세 (Sitting)", 4),
    STANDING("서 있는 자세 (Standing)", 5)
}
