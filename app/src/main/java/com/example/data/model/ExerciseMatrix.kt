package com.example.data.model

data class MatrixExercise(
    val id: String,
    val positionStage: Int, // 1: Supine/Prone, 2: Prone/Quadruped, 3: Quadruped/Sitting, 4: Sitting/Standing
    val resistanceStage: Int, // 1: 체중/호흡, 2: 중력/경량밴드, 3: 소도구/볼/덤벨, 4: 일상동작 기능부하
    val title: String,
    val postureLabel: String,
    val resistanceLabel: String,
    val durationSeconds: Int,
    val repetitions: String,
    val instructions: List<String>,
    val safetyTip: String,
    val targetBenefit: String,
    val monthVariant: Int // 1: 1달차(기초), 2: 2달차(변형/심화), 3: 3달차(소도구/일상전이)
)

data class MatrixCell(
    val positionStage: Int,
    val positionName: String,
    val resistanceStage: Int,
    val resistanceName: String,
    val exercises: List<MatrixExercise>
)

object MatrixRepository {
    val matrixExercises: List<MatrixExercise> = listOf(
        // === POSITION 1 (Supine / Prone) ===
        // P1 x R1 (1달차)
        MatrixExercise(
            id = "m1_p1_r1",
            positionStage = 1,
            resistanceStage = 1,
            title = "누워서 90-90 복식 호흡 & 골반 틸트",
            postureLabel = "SUPINE (누운 자세)",
            resistanceLabel = "R1: 체중 지지 & 호흡 가동성",
            durationSeconds = 40,
            repetitions = "호흡 10회 (3세트)",
            instructions = listOf(
                "편안하게 누워 무릎을 세우고 양발을 골반 너비로 둡니다.",
                "코로 숨을 깊게 들이쉬며 배를 둥글게 부풀리고, 입으로 천천히 내쉽니다.",
                "내쉬는 숨에 허리가 바닥에 가볍게 밀착되도록 골반을 살짝 말아줍니다."
            ),
            safetyTip = "허리에 날카로운 통증이 느껴지면 범위를 줄여 부드럽게 호흡만 진행하세요.",
            targetBenefit = "척추 감압 및 횡격막 코어 활성화",
            monthVariant = 1
        ),
        // P1 x R2 (1달차 후면)
        MatrixExercise(
            id = "m1_p1_r2",
            positionStage = 1,
            resistanceStage = 2,
            title = "엎드려 다리 교차 들기 (프론 레그 리프트)",
            postureLabel = "PRONE (엎드린 자세)",
            resistanceLabel = "R2: 중력 대항 기초 부하",
            durationSeconds = 45,
            repetitions = "좌우 8회씩 3세트",
            instructions = listOf(
                "매트에 엎드려 이마 아래 손을 포개어 목의 긴장을 풉니다.",
                "아랫배를 살짝 바닥에서 띄운다는 느낌으로 둔근에 힘을 줍니다.",
                "한쪽 다리를 엉덩이 높이까지만 천천히 들어 2초 버틴 후 내립니다."
            ),
            safetyTip = "허리가 꺾이지 않도록 배꼽을 척추 쪽으로 당긴 상태를 유지하세요.",
            targetBenefit = "둔근 활성화 및 허리 후면사슬 지지력 강화",
            monthVariant = 1
        ),
        // P1 x R2 (2달차 변형)
        MatrixExercise(
            id = "m2_p1_r2",
            positionStage = 1,
            resistanceStage = 2,
            title = "누워서 싱글 레그 브릿지 & 밴드 지지",
            postureLabel = "SUPINE (누운 자세)",
            resistanceLabel = "R2: 중력 + 탄성 지지",
            durationSeconds = 50,
            repetitions = "좌우 6회씩 3세트",
            instructions = listOf(
                "누운 상태에서 엉덩이를 들어 브릿지 자세를 만듭니다.",
                "골반 수평을 유지하며 한쪽 발을 살짝 바닥에서 떼어 유지합니다.",
                "천천히 내려오며 엉덩이와 햄스트링의 긴장감을 느낍니다."
            ),
            safetyTip = "햄스트링에 쥐가 날 것 같으면 양발 브릿지로 전환하세요.",
            targetBenefit = "골반 좌우 대칭 안정성 및 둔근 집중",
            monthVariant = 2
        ),
        // P1 x R3 (3달차 소도구)
        MatrixExercise(
            id = "m3_p1_r3",
            positionStage = 1,
            resistanceStage = 3,
            title = "미니볼을 무릎 사이에 낀 브릿지 & 흉추 확장",
            postureLabel = "SUPINE (누운 자세)",
            resistanceLabel = "R3: 소도구(미니볼) 저항",
            durationSeconds = 50,
            repetitions = "10회 3세트",
            instructions = listOf(
                "양 무릎 사이에 미니볼(또는 베개)을 끼우고 눕습니다.",
                "볼을 30% 힘으로 부드럽게 조이며 엉덩이를 들어 올립니다.",
                "팔을 머리 위로 뻗으며 흉추를 시원하게 스트레칭합니다."
            ),
            safetyTip = "볼을 너무 세게 쥐어짜지 않고 내전근의 가벼운 수축만 유도합니다.",
            targetBenefit = "내전근-골반저근 협응 및 흉추 신전 확장",
            monthVariant = 3
        ),

        // === POSITION 2 (Prone / Quadruped) ===
        // P2 x R1 (1달차)
        MatrixExercise(
            id = "m1_p2_r1",
            positionStage = 2,
            resistanceStage = 1,
            title = "네발기기 캣-카우 & 척추 분절 가동화",
            postureLabel = "QUADRUPED (네발기기)",
            resistanceLabel = "R1: 체중 지지 척추 분절",
            durationSeconds = 45,
            repetitions = "8회 반복 (천천히)",
            instructions = listOf(
                "손목은 어깨 아래, 무릎은 골반 아래에 둡니다.",
                "숨을 내쉬며 등을 둥글게 말아 올리고 시선은 배꼽을 바라봅니다.",
                "숨을 들이쉬며 가슴을 활짝 열고 꼬리뼈를 살짝 들어 올립니다."
            ),
            safetyTip = "손목에 부담이 있다면 주먹을 쥐거나 팔꿈치를 대고 진행하세요.",
            targetBenefit = "척추 전 분절 굴곡-신전 유연성 회복",
            monthVariant = 1
        ),
        // P2 x R2 (1달차)
        MatrixExercise(
            id = "m1_p2_r2",
            positionStage = 2,
            resistanceStage = 2,
            title = "네발기기 버드독 (대각선 팔다리 뻗기)",
            postureLabel = "QUADRUPED (네발기기)",
            resistanceLabel = "R2: 중력 대항 체간 안정성",
            durationSeconds = 50,
            repetitions = "좌우 교차 8회씩",
            instructions = listOf(
                "네발기기 자세에서 몸통이 흔들리지 않게 중심을 잡습니다.",
                "오른팔과 왼다리를 바닥과 평행하게 천천히 뻗습니다.",
                "3초간 정지하여 코어를 조인 후 시작 자세로 돌아옵니다."
            ),
            safetyTip = "몸통이 옆으로 기울어지지 않도록 유리컵을 등에 얹었다고 상상하세요.",
            targetBenefit = "대각선 후면 코어 안정성 및 낙상 예방 협응",
            monthVariant = 1
        ),
        // P2 x R2 (2달차 변형)
        MatrixExercise(
            id = "m2_p2_r2",
            positionStage = 2,
            resistanceStage = 2,
            title = "네발기기 스레드 더 니들 (흉추 회전 가동화)",
            postureLabel = "QUADRUPED (네발기기)",
            resistanceLabel = "R2: 다방향 회전 안정성",
            durationSeconds = 50,
            repetitions = "좌우 6회씩 2세트",
            instructions = listOf(
                "네발기기 자세에서 한쪽 손을 반대편 겨드랑이 아래로 통과시킵니다.",
                "어깨와 관자놀이가 바닥에 부드럽게 닿도록 흉추를 회전합니다.",
                "반대로 팔을 천장 쪽으로 열며 시선도 손끝을 따라갑니다."
            ),
            safetyTip = "목을 꺾지 않고 등 윗부분(흉추)의 회전에 집중하세요.",
            targetBenefit = "굽은 등 교정 및 흉추 회전 제한 완화",
            monthVariant = 2
        ),
        // P2 x R3 (3달차 소도구)
        MatrixExercise(
            id = "m3_p2_r3",
            positionStage = 2,
            resistanceStage = 3,
            title = "탄성 루프밴드를 활용한 네발기기 힙 킥백",
            postureLabel = "QUADRUPED (네발기기)",
            resistanceLabel = "R3: 소도구(밴드) 점진 저항",
            durationSeconds = 50,
            repetitions = "좌우 8회씩 3세트",
            instructions = listOf(
                "양 발목 또는 무릎 위쪽에 가벼운 강도의 루프밴드를 겁니다.",
                "네발기기 자세에서 한쪽 다리를 뒤로 밀어내며 밴드 저항을 느낍니다.",
                "허리가 꺾이지 않는 범위까지만 차올리고 천천히 돌아옵니다."
            ),
            safetyTip = "밴드 탄성이 너무 강하면 맨몸으로 진행하세요.",
            targetBenefit = "보행 추진력 증진 및 중둔근 강화",
            monthVariant = 3
        ),

        // === POSITION 3 (Quadruped / Sitting) ===
        // P3 x R1 (1달차)
        MatrixExercise(
            id = "m1_p3_r1",
            positionStage = 3,
            resistanceStage = 1,
            title = "의자에 바르게 앉아 척추 키 늘리기 & 골반 인지",
            postureLabel = "SITTING (앉은 자세)",
            resistanceLabel = "R1: 좌식 중력 적응 & 척추 정렬",
            durationSeconds = 45,
            repetitions = "호흡 8회 2세트",
            instructions = listOf(
                "의자 앞쪽 1/3 지점에 앉아 양발바닥을 지면에 단단히 붙입니다.",
                "좌골(엉덩이 뼈) 두 개로 체중을 균등하게 지지합니다.",
                "정수리를 천장으로 누가 당기듯 척추를 길게 펴고 어깨는 내립니다."
            ),
            safetyTip = "허리를 과하게 꺾지 말고 배에 가볍게 긴장감을 유지하세요.",
            targetBenefit = "올바른 좌식 척추 중립 인지 및 디스크 압박 감소",
            monthVariant = 1
        ),
        // P3 x R2 (1달차)
        MatrixExercise(
            id = "m1_p3_r2",
            positionStage = 3,
            resistanceStage = 2,
            title = "앉아서 몸통 트위스트 & 흉추 오픈",
            postureLabel = "SITTING (앉은 자세)",
            resistanceLabel = "R2: 중력 대항 회전 안정성",
            durationSeconds = 50,
            repetitions = "좌우 6회씩 3세트",
            instructions = listOf(
                "의자에 바르게 앉아 양손을 가슴 앞 'X'자로 포갭니다.",
                "골반과 무릎은 정면을 고정한 채 몸통만 천천히 오른쪽으로 회전합니다.",
                "3초 머무른 후 중앙으로 돌아와 반대쪽으로 회전합니다."
            ),
            safetyTip = "골반이 함께 돌아가지 않도록 무릎 방향을 유지하세요.",
            targetBenefit = "운전 및 뒤돌아보기 등 일상 회전 기능 개선",
            monthVariant = 1
        ),
        // P3 x R2 (2달차 변형)
        MatrixExercise(
            id = "m2_p3_r2",
            positionStage = 3,
            resistanceStage = 2,
            title = "앉아서 대퇴사두근 레그 익스텐션 & 발목 펌핑",
            postureLabel = "SITTING (앉은 자세)",
            resistanceLabel = "R2: 하지 신전 저항",
            durationSeconds = 50,
            repetitions = "좌우 10회씩 3세트",
            instructions = listOf(
                "의자에 앉아 한쪽 무릎을 앞으로 쭉 펴서 허벅지 앞쪽에 힘을 줍니다.",
                "발끝을 몸쪽으로 당겼다가 앞으로 밀어주는 발목 펌핑을 2회 합니다.",
                "천천히 다리를 내리고 반대쪽을 진행합니다."
            ),
            safetyTip = "무릎 관절에 시큰거림이 있으면 90% 정도만 펴세요.",
            targetBenefit = "무릎 관절염 예방 및 종아리 혈액순환 개선",
            monthVariant = 2
        ),
        // P3 x R3 (3달차 소도구)
        MatrixExercise(
            id = "m3_p3_r3",
            positionStage = 3,
            resistanceStage = 3,
            title = "앉아서 밴드 로우 (등 당기기) & 어깨 외회전",
            postureLabel = "SITTING (앉은 자세)",
            resistanceLabel = "R3: 소도구(세라밴드) 상체 저항",
            durationSeconds = 50,
            repetitions = "10회씩 3세트",
            instructions = listOf(
                "양 발바닥 중앙에 탄성 밴드를 걸고 양손으로 끝을 잡습니다.",
                "허리를 곧게 편 채 팔꿈치를 뒤로 당기며 날개뼈를 모아줍니다.",
                "어깨가 솟아오르지 않게 귀와 어깨를 멀리 유지하며 2초 정지합니다."
            ),
            safetyTip = "어깨를 으쓱거리지 말고 등 뒤쪽 근육을 의식하세요.",
            targetBenefit = "라운드 숄더 개선 및 상체 당기는 일상 근력 향상",
            monthVariant = 3
        ),

        // === POSITION 4 (Sitting / Standing) ===
        // P4 x R1 (1달차)
        MatrixExercise(
            id = "m1_p4_r1",
            positionStage = 4,
            resistanceStage = 1,
            title = "의자 잡고 한 발 서기 (싱글 레그 스탠스 밸런스)",
            postureLabel = "STANDING (서 있는 자세)",
            resistanceLabel = "R1: 지면 반력 & 고유수용감각",
            durationSeconds = 45,
            repetitions = "좌우 15초씩 버티기 (3세트)",
            instructions = listOf(
                "안전한 의자 등받이를 가볍게 손으로 터치하며 섭니다.",
                "한쪽 발을 지면에서 5cm 들어 올리고 서 있는 발바닥에 집중합니다.",
                "시선은 정면 2m 앞 고정된 점을 바라보며 흔들림을 최소화합니다."
            ),
            safetyTip = "중심을 잃으면 언제든 의자를 꽉 잡거나 반대 발을 딛으세요.",
            targetBenefit = "낙상 방지 신경근 활성화 및 발목 고유수용감각 강화",
            monthVariant = 1
        ),
        // P4 x R2 (1달차)
        MatrixExercise(
            id = "m1_p4_r2",
            positionStage = 4,
            resistanceStage = 2,
            title = "체어 시트 투 스탠드 (의자 앉았다 일어서기)",
            postureLabel = "STANDING (서 있는 자세)",
            resistanceLabel = "R2: 체중 전신 중력 저항",
            durationSeconds = 50,
            repetitions = "8~10회 3세트",
            instructions = listOf(
                "발을 어깨너비로 벌리고 의자에 앉습니다.",
                "상체를 살짝 앞으로 숙여 체중을 발바닥 중앙에 싣습니다.",
                "발바닥으로 지면을 강하게 밀며 일어서서 엉덩이를 꽉 조입니다.",
                "앉을 때는 엉덩이를 뒤로 빼며 천천히 통제하며 앉습니다."
            ),
            safetyTip = "무릎이 안쪽으로 모이지 않게 두 번째 발가락 방향을 유지하세요.",
            targetBenefit = "하지 전신 근력 및 일상 기립 기능 독립성 유지",
            monthVariant = 1
        ),
        // P4 x R3 (2달차 변형)
        MatrixExercise(
            id = "m2_p4_r3",
            positionStage = 4,
            resistanceStage = 3,
            title = "스탠딩 텐덤 보행 & 덤벨 가벼운 지지",
            postureLabel = "STANDING (서 있는 자세)",
            resistanceLabel = "R3: 동적 이동 밸런스 + 경량 부하",
            durationSeconds = 50,
            repetitions = "앞뒤 8걸음씩 3세트",
            instructions = listOf(
                "벽이나 손잡이 옆에서 한쪽 발의 뒤꿈치를 반대 발의 앞코에 닿게 섭니다.",
                "일자 선을 따라 천천히 앞뒤로 발을 딛으며 균형을 잡습니다.",
                "양손에 500ml 물병이나 0.5kg 아령을 쥐고 흔들림을 억제합니다."
            ),
            safetyTip = "반드시 옆에 짚을 수 있는 벽이나 난간이 있는 곳에서 실시합니다.",
            targetBenefit = "좁은 지지면에서의 동적 균형 및 보행 안정성",
            monthVariant = 2
        ),
        // P4 x R4 (3달차 일상생활 ADL 전이)
        MatrixExercise(
            id = "m3_p4_r4",
            positionStage = 4,
            resistanceStage = 4,
            title = "스텝박스 오르내리기 & 장바구니 들기 (ADL 기능 훈련)",
            postureLabel = "STANDING (서 있는 자세)",
            resistanceLabel = "R4: 일상생활(ADL) 전이 복합 부하",
            durationSeconds = 60,
            repetitions = "좌우 8회씩 3세트",
            instructions = listOf(
                "낮은 계단이나 10cm 스텝 발판 앞에 섭니다.",
                "한 손에 가벼운 가방을 들고 발바닥 전체로 계단을 딛고 올라섭니다.",
                "올라선 후 무릎을 곧게 펴고 반대 발을 천천히 내립니다."
            ),
            safetyTip = "계단을 내려올 때 쿵 소리가 나지 않게 사두근으로 천천히 버티세요.",
            targetBenefit = "계단 오르내리기, 쇼핑, 대중교통 이용 등 실제 생활 기능 완성",
            monthVariant = 3
        )
    )

    fun getExercise(positionStage: Int, resistanceStage: Int, monthVariant: Int = 1): MatrixExercise {
        return matrixExercises.firstOrNull {
            it.positionStage == positionStage &&
            it.resistanceStage == resistanceStage &&
            it.monthVariant == monthVariant
        } ?: matrixExercises.firstOrNull {
            it.positionStage == positionStage && it.resistanceStage == resistanceStage
        } ?: matrixExercises.first()
    }
}
