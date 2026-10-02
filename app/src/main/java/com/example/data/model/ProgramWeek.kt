package com.example.data.model

data class WeekProgram(
    val weekNumber: Int, // 1 ~ 12
    val monthNumber: Int, // 1 ~ 3
    val title: String,
    val positionFocus: String, // e.g. "SUPINE & PRONE"
    val resistanceFocus: String, // e.g. "R1: 체중 지지 & 호흡 가동성"
    val themeDescription: String,
    val isEvaluationWeek: Boolean,
    val evaluationTitle: String? = null,
    val exercises: List<MatrixExercise>
)

object ProgramRoadmap {
    val weeks: List<WeekProgram> = listOf(
        // === MONTH 1 ===
        WeekProgram(
            weekNumber = 1,
            monthNumber = 1,
            title = "1주차: 척추 감압 & 호흡 코어 기초",
            positionFocus = "SUPINE & PRONE (누운/엎드린 자세)",
            resistanceFocus = "R1: 체중 지지 & 호흡 가동성",
            themeDescription = "지면과 몸이 밀착된 상태에서 척추 부담 없이 횡격막 호흡과 골반 중립을 학습합니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(1, 1, 1),
                MatrixRepository.getExercise(1, 2, 1)
            )
        ),
        WeekProgram(
            weekNumber = 2,
            monthNumber = 1,
            title = "2주차: 후면 사슬 & 4지 지지 체간 안정",
            positionFocus = "PRONE & QUADRUPED (엎드린/네발기기 자세)",
            resistanceFocus = "R2: 중력 대항 안정성",
            themeDescription = "네발기기 자세에서 어깨와 골반의 지지력을 다지고 대각선 코어 연결을 활성화합니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(2, 1, 1),
                MatrixRepository.getExercise(2, 2, 1)
            )
        ),
        WeekProgram(
            weekNumber = 3,
            monthNumber = 1,
            title = "3주차: 수직 중력 적응 & 흉추 회전",
            positionFocus = "QUADRUPED & SITTING (네발기기/앉은 자세)",
            resistanceFocus = "R1-R2: 좌식 정렬 & 회전 가동성",
            themeDescription = "의자에 앉아 수직으로 가해지는 중력을 올바르게 분산하고 척추 회전 가동 범위를 엽니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(3, 1, 1),
                MatrixRepository.getExercise(3, 2, 1)
            )
        ),
        WeekProgram(
            weekNumber = 4,
            monthNumber = 1,
            title = "4주차: 기립 지지 & 의자 일어서기",
            positionFocus = "SITTING & STANDING (앉은/서 있는 자세)",
            resistanceFocus = "R2: 체중 전신 중력 부하",
            themeDescription = "의자에서 안전하게 일어나고 한 발로 서서 체중을 지탱하는 기본 밸런스를 습득합니다.",
            isEvaluationWeek = true,
            evaluationTitle = "4주 중간 점검 미니 평가 (기초 가동성 & 통증 변화 확인)",
            exercises = listOf(
                MatrixRepository.getExercise(4, 1, 1),
                MatrixRepository.getExercise(4, 2, 1)
            )
        ),

        // === MONTH 2 ===
        WeekProgram(
            weekNumber = 5,
            monthNumber = 2,
            title = "5주차: 누운 자세 편측 둔근 집중",
            positionFocus = "SUPINE & PRONE (누운/엎드린 자세)",
            resistanceFocus = "R2: 편측 지지 & 저항 변형",
            themeDescription = "1주차의 자유도를 반복하되, 한 발 브릿지와 같은 동작 변형을 통해 둔근 집중도를 높입니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(1, 2, 2),
                MatrixRepository.getExercise(1, 1, 1)
            )
        ),
        WeekProgram(
            weekNumber = 6,
            monthNumber = 2,
            title = "6주차: 흉추 오픈 & 다방향 회전 안정",
            positionFocus = "PRONE & QUADRUPED (엎드린/네발기기 자세)",
            resistanceFocus = "R2: 다방향 회전 가동화",
            themeDescription = "네발기기에서 흉추를 시원하게 회전하고 어깨와 견갑골의 안정성을 심화합니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(2, 2, 2),
                MatrixRepository.getExercise(2, 1, 1)
            )
        ),
        WeekProgram(
            weekNumber = 7,
            monthNumber = 2,
            title = "7주차: 하지 신전 & 관절 순환 강화",
            positionFocus = "QUADRUPED & SITTING (네발기기/앉은 자세)",
            resistanceFocus = "R2: 무릎 관절 보호 & 발목 펌핑",
            themeDescription = "앉은 자세에서 대퇴사두근을 안전하게 강화하고 하지 부종 및 혈액순환을 촉진합니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(3, 2, 2),
                MatrixRepository.getExercise(3, 1, 1)
            )
        ),
        WeekProgram(
            weekNumber = 8,
            monthNumber = 2,
            title = "8주차: 텐덤 보행 & 동적 밸런스 확장",
            positionFocus = "SITTING & STANDING (앉은/서 있는 자세)",
            resistanceFocus = "R3: 동적 이동 & 경량 부하",
            themeDescription = "일자 걷기(텐덤 보행)와 무게중심 이동을 통해 좁은 길이나 야외 보행의 안정성을 구축합니다.",
            isEvaluationWeek = true,
            evaluationTitle = "8주 중간 점검 미니 평가 (균형 감각 및 보행 신뢰도 확인)",
            exercises = listOf(
                MatrixRepository.getExercise(4, 3, 2),
                MatrixRepository.getExercise(4, 2, 1)
            )
        ),

        // === MONTH 3 ===
        WeekProgram(
            weekNumber = 9,
            monthNumber = 3,
            title = "9주차: 미니볼을 활용한 내전근-골반저근 협응",
            positionFocus = "SUPINE & PRONE (누운/엎드린 자세)",
            resistanceFocus = "R3: 소도구(미니볼) 저항",
            themeDescription = "소도구(볼/쿠션)를 활용하여 척추 안정과 직결되는 골반저근과 내전근을 강화합니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(1, 3, 3),
                MatrixRepository.getExercise(1, 2, 1)
            )
        ),
        WeekProgram(
            weekNumber = 10,
            monthNumber = 3,
            title = "10주차: 탄성 밴드 저항 힙 킥백 & 보행 추진력",
            positionFocus = "PRONE & QUADRUPED (엎드린/네발기기 자세)",
            resistanceFocus = "R3: 소도구(루프밴드) 탄성 저항",
            themeDescription = "밴드 탄성을 이겨내며 둔근을 수축시켜 일상에서 힘차게 앞으로 걷는 추진력을 만듭니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(2, 3, 3),
                MatrixRepository.getExercise(2, 2, 1)
            )
        ),
        WeekProgram(
            weekNumber = 11,
            monthNumber = 3,
            title = "11주차: 세라밴드 로우 & 굽은 등 펴기",
            positionFocus = "QUADRUPED & SITTING (네발기기/앉은 자세)",
            resistanceFocus = "R3: 소도구(세라밴드) 상체 저항",
            themeDescription = "밴드를 당겨 등 근육을 일깨우고 물건을 당기거나 안아 올리는 일상 상체 근력을 보강합니다.",
            isEvaluationWeek = false,
            exercises = listOf(
                MatrixRepository.getExercise(3, 3, 3),
                MatrixRepository.getExercise(3, 1, 1)
            )
        ),
        WeekProgram(
            weekNumber = 12,
            monthNumber = 3,
            title = "12주차: 스텝박스 & 장바구니 들기 (ADL 완성)",
            positionFocus = "SITTING & STANDING (앉은/서 있는 자세)",
            resistanceFocus = "R4: 일상생활(ADL) 전이 복합 부하",
            themeDescription = "계단 오르기, 짐 들고 걷기 등 실생활 동작을 안전하고 자신감 있게 수행하도록 완성합니다.",
            isEvaluationWeek = true,
            evaluationTitle = "12주 최종 종합 재평가 (88웰니스 바디체크 전 종목 풀 리테스트 & 수료 리포트)",
            exercises = listOf(
                MatrixRepository.getExercise(4, 4, 3),
                MatrixRepository.getExercise(4, 2, 1)
            )
        )
    )
}
