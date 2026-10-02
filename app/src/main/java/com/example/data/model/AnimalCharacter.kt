package com.example.data.model

data class AnimalCharacter(
    val id: String,
    val mbtiCode: String,
    val name: String,
    val summary: String,
    val description: String,
    val mobilityScore: Int, // 0~100
    val stabilityScore: Int, // 0~100
    val balanceScore: Int, // 0~100
    val powerScore: Int, // 0~100
    val strengths: List<String>,
    val cautionPoints: List<String>,
    val recommendedFocus: String,
    val matrixStartStage: String,
    val characterEmoji: String,
    val badgeColorHex: Long
)

object AnimalCharacters {
    val SLOTH = AnimalCharacter(
        id = "sloth",
        mbtiCode = "M-STA (유연성형)",
        name = "유연한 나무늘보",
        summary = "부드러운 관절 가동성을 지녔으나 코어 중심 지지력이 필요한 힐링 체질",
        description = "몸이 굳어있지 않고 부드러운 편이지만, 척추를 단단하게 잡아주는 코어 안정성과 둔근 지지력이 부족하여 오래 서 있거나 보행 시 피로를 쉽게 느낄 수 있습니다. 바닥 자세(Supine/Prone)부터 코어 지지력을 차근차근 다지는 것이 최고의 처방입니다.",
        mobilityScore = 88,
        stabilityScore = 48,
        balanceScore = 55,
        powerScore = 50,
        strengths = listOf("관절 유연성과 부드러움", "스트레칭 적응력 우수", "근육 긴장도 낮음"),
        cautionPoints = listOf("허리가 과도하게 꺾이지 않도록 복압 유지", "무릎 과신전 주의"),
        recommendedFocus = "복부 복압(Bracing) 및 둔근 등척성 안정성 훈련",
        matrixStartStage = "1단계: 누운 자세 코어 세팅 & 호흡 저항",
        characterEmoji = "🦥",
        badgeColorHex = 0xFF10B981
    )

    val TURTLE = AnimalCharacter(
        id = "turtle",
        mbtiCode = "S-MOB (안정성형)",
        name = "듬직한 거북이",
        summary = "단단한 중심 안정성을 가졌으나 척추와 고관절 가동 범위 확장이 필요한 타입",
        description = "체간과 골반의 지지력과 지구력은 듬직하지만, 흉추 신전과 고관절 굴곡 가동 범위가 다소 굳어있어 웅크린 동작이나 허리를 펼 때 뻣뻣함을 느낍니다. 척추의 굴곡-신전을 점진적으로 확장하는 4단계 자유도 운동이 효과적입니다.",
        mobilityScore = 45,
        stabilityScore = 85,
        balanceScore = 68,
        powerScore = 60,
        strengths = listOf("뛰어난 코어 지구력", "관절의 안정된 고정력", "차분한 동작 수행"),
        cautionPoints = listOf("갑작스러운 회전이나 과도한 신전 제한", "반동 없이 부드럽게 이완"),
        recommendedFocus = "흉추 가동성(회전/신전) 및 햄스트링/고관절 릴리즈",
        matrixStartStage = "1단계: 누운 자세 흉추 스트레칭 & 저항 가동성",
        characterEmoji = "🐢",
        badgeColorHex = 0xFF0D5BFF
    )

    val CRANE = AnimalCharacter(
        id = "crane",
        mbtiCode = "B-STA (밸런스집중형)",
        name = "우아한 두루미",
        summary = "상체 정렬과 유연성은 훌륭하나 한 발 지지력과 하지 협응력이 필요한 타입",
        description = "전체적인 몸매 정렬과 상체 움직임은 곧고 우아하지만, 지면을 지지하는 발목-무릎-골반의 편측(Single Leg) 안정성이 다소 불안정합니다. 좌우 대칭성을 맞추고 단계별 체중 지지 운동을 통해 낙상 예방과 보행 안정성을 극대화합니다.",
        mobilityScore = 78,
        stabilityScore = 65,
        balanceScore = 45,
        powerScore = 58,
        strengths = listOf("척추 축 정렬 우수", "상하체 협응 감각 양호", "경쾌한 움직임"),
        cautionPoints = listOf("발목 흔들림 주의", "한 발 지지 시 골반 수평 유지"),
        recommendedFocus = "하지 단일 지지 안정성(Single Leg Stance) 및 둔근 외전근 강화",
        matrixStartStage = "2단계: 네발기기 교차 지지 & 밴드 밸런스",
        characterEmoji = "🦩",
        badgeColorHex = 0xFF8B5CF6
    )

    val KOALA = AnimalCharacter(
        id = "koala",
        mbtiCode = "C-EXT (자세확장형)",
        name = "신중한 코알라",
        summary = "웅크린 좌식 생활로 흉추 회전과 가슴 폄이 필요한 포근한 안정형",
        description = "의자에 오래 앉아 있는 생활이나 정적인 취미로 인해 어깨가 앞으로 말리고 흉추가 굽어있는 경향이 있습니다. 네발기기(Quadruped)와 앉은 자세(Sitting)에서 가슴을 활짝 열고 척추를 회전하는 운동을 통해 호흡과 활력을 되찾습니다.",
        mobilityScore = 52,
        stabilityScore = 62,
        balanceScore = 60,
        powerScore = 52,
        strengths = listOf("섬세한 신체 컨트롤", "부상 위험 낮은 안전성", "꾸준한 실행력"),
        cautionPoints = listOf("목을 앞으로 빼지 않기", "등 뒤 날개뼈 모으기 집중"),
        recommendedFocus = "흉추 신전/회전 운동 및 능형근/광배근 활성화",
        matrixStartStage = "2단계: 엎드린/네발기기 자세 흉추 오픈",
        characterEmoji = "🐨",
        badgeColorHex = 0xFFF59E0B
    )

    val TIGER = AnimalCharacter(
        id = "tiger",
        mbtiCode = "P-CON (파워제어형)",
        name = "당당한 호랑이",
        summary = "추진력과 근력은 강하지만 관절 충격 완화와 안전 감속 제어가 필수인 타입",
        description = "에너지가 넘치고 큰 근육의 힘은 좋으나, 동작을 멈추고 감속할 때 관절에 무리가 가거나 경미한 통증 신호가 나타날 수 있습니다. 통증 없는 가동 범위(Pain-free zone)를 지키며 소도구와 중력 저항을 점진적으로 다루는 것이 핵심입니다.",
        mobilityScore = 60,
        stabilityScore = 70,
        balanceScore = 65,
        powerScore = 88,
        strengths = listOf("강한 하지 추진력", "적극적인 운동 의지", "근육 반응 속도 우수"),
        cautionPoints = listOf("통증 구간 무리한 진행 금지", "관절 쿵쿵 딛지 않기"),
        recommendedFocus = "편심성(Eccentric) 감속 제어 및 통증 없는 가동 범위 유지",
        matrixStartStage = "3단계: 앉은/선 자세 기능성 저항 제어",
        characterEmoji = "🐯",
        badgeColorHex = 0xFFEA580C
    )

    val SQUIRREL = AnimalCharacter(
        id = "squirrel",
        mbtiCode = "A-DUR (민첩지구형)",
        name = "날렵한 다람쥐",
        summary = "빠르고 기동성이 좋으나 전신 체중 부하 분산과 코어 지속력이 필요한 타입",
        description = "일상에서 손발이 빠르고 부지런하지만, 큰 관절의 지지 근력이 빨리 지치는 경향이 있습니다. 4x4 매트릭스의 낮은 자유도에서 안정적인 호흡 패턴과 지지력을 충분히 익힌 뒤 기립 자세로 전환하면 활력이 배가됩니다.",
        mobilityScore = 72,
        stabilityScore = 55,
        balanceScore = 62,
        powerScore = 66,
        strengths = listOf("민첩한 반응성", "기동성 및 보행 템포", "유연한 체중 이동"),
        cautionPoints = listOf("서두르지 말고 호흡에 맞춰 템포 조절", "허리 반동 자제"),
        recommendedFocus = "호흡 기반 횡격막 코어 안정성 및 단계적 부하 누적",
        matrixStartStage = "1단계: 누운/엎드린 자세 호흡 템포 훈련",
        characterEmoji = "🐿️",
        badgeColorHex = 0xFF0284C7
    )

    fun determineCharacter(
        hasPain: Boolean,
        mobilityIssue: Boolean,
        stabilityIssue: Boolean,
        balanceIssue: Boolean,
        powerIssue: Boolean
    ): AnimalCharacter {
        return when {
            hasPain && powerIssue -> TIGER
            mobilityIssue && !stabilityIssue -> TURTLE
            !mobilityIssue && stabilityIssue -> SLOTH
            balanceIssue -> CRANE
            mobilityIssue && stabilityIssue -> KOALA
            else -> SQUIRREL
        }
    }
}
