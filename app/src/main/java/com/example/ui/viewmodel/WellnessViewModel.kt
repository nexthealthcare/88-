package com.example.ui.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.AssessmentEntity
import com.example.data.local.UserEntity
import com.example.data.local.WorkoutLogEntity
import com.example.data.model.AnimalCharacter
import com.example.data.model.AnimalCharacters
import com.example.data.model.LaborIntensity
import com.example.data.model.MatrixExercise
import com.example.data.model.MatrixRepository
import com.example.data.model.MovementCategory
import com.example.data.model.PostureStage
import com.example.data.model.ProgramRoadmap
import com.example.data.model.SfmaMovement
import com.example.data.model.SurveyDemographics
import com.example.data.model.WeekProgram
import com.example.data.repository.WellnessRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppScreen {
    HOME,
    SURVEY_DEMOGRAPHICS,
    SURVEY_SFMA,
    RESULT,
    AUTH,
    ROADMAP,
    MATRIX_EXPLORER,
    WORKOUT_PLAYER,
    CENTER_INFO
}

data class SfmaQuestion(
    val movement: SfmaMovement,
    val posture: PostureStage
)

class WellnessViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

    private val repository: WellnessRepository
    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    init {
        val dao = AppDatabase.getInstance(application).wellnessDao()
        repository = WellnessRepository(dao)
        try {
            tts = TextToSpeech(application, this)
        } catch (_: Exception) {}
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.KOREAN)
            isTtsReady = (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED)
        }
    }

    fun speakText(text: String) {
        if (isTtsReady) {
            tts?.stop()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_wellness")
        }
    }

    fun stopSpeaking() {
        tts?.stop()
    }

    override fun onCleared() {
        super.onCleared()
        tts?.stop()
        tts?.shutdown()
        timerJob?.cancel()
    }

    // --- State variables ---
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _isLargeFontMode = MutableStateFlow(false)
    val isLargeFontMode: StateFlow<Boolean> = _isLargeFontMode.asStateFlow()

    fun toggleLargeFontMode() {
        _isLargeFontMode.value = !_isLargeFontMode.value
    }

    // User & Auth State
    val currentUser: StateFlow<UserEntity?> = repository.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val latestAssessment: StateFlow<AssessmentEntity?> = repository.latestAssessment
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val workoutLogs: StateFlow<List<WorkoutLogEntity>> = repository.allWorkoutLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Demographics Survey State
    private val _demographics = MutableStateFlow(SurveyDemographics())
    val demographics: StateFlow<SurveyDemographics> = _demographics.asStateFlow()

    fun updateAge(age: Int) {
        _demographics.value = _demographics.value.copy(age = age)
    }

    fun updateGender(gender: String) {
        _demographics.value = _demographics.value.copy(gender = gender)
    }

    fun updateEmail(email: String) {
        _demographics.value = _demographics.value.copy(email = email)
    }

    fun updateLaborIntensity(intensity: LaborIntensity) {
        _demographics.value = _demographics.value.copy(laborIntensity = intensity)
    }

    fun togglePainArea(area: String) {
        val current = _demographics.value.painAreas.toMutableSet()
        if (current.contains(area)) current.remove(area) else current.add(area)
        _demographics.value = _demographics.value.copy(painAreas = current)
    }

    fun updateExerciseFrequency(frequency: String) {
        _demographics.value = _demographics.value.copy(exerciseFrequency = frequency)
    }

    // SFMA Questions (5 movements across 5 postures)
    val sfmaQuestions: List<SfmaQuestion> = listOf(
        SfmaQuestion(
            movement = SfmaMovement(
                id = "flexion",
                title = "1. FLEXION (척추 굴곡 & 후면 체인)",
                category = MovementCategory.FLEXION,
                targetPostures = "SUPINE (누워서) -> STANDING (서서 숙이기)",
                guideDescription = "상체를 부드럽게 앞으로 숙여 손끝이 무릎 아래 또는 발끝 방향으로 자연스럽게 내려가는지 확인합니다.",
                checkPoint = "허리가 둥글게 자연스러운 곡선을 그리고 허벅지 뒤쪽(햄스트링) 당김이 견딜 만한가요?",
                iconEmoji = "🧘"
            ),
            posture = PostureStage.STANDING
        ),
        SfmaQuestion(
            movement = SfmaMovement(
                id = "extension",
                title = "2. EXTENSION (척추 신전 & 가슴 젖히기)",
                category = MovementCategory.EXTENSION,
                targetPostures = "PRONE (엎드려서) -> STANDING (서서 젖히기)",
                guideDescription = "양손을 골반 뒤에 얹고 가슴을 천장 방향으로 부드럽게 젖혀 몸의 앞쪽 사슬을 열어줍니다.",
                checkPoint = "허리가 꺾이지 않고 등 윗부분(흉추)이 시원하게 펴지며 호흡이 편안한가요?",
                iconEmoji = "🦒"
            ),
            posture = PostureStage.PRONE
        ),
        SfmaQuestion(
            movement = SfmaMovement(
                id = "rotation",
                title = "3. ROTATION (몸통 좌우 회전 가동성)",
                category = MovementCategory.ROTATION,
                targetPostures = "QUADRUPED (네발기기) -> SITTING (앉아서 회전)",
                guideDescription = "의자에 바르게 앉아 골반을 고정한 채 몸통을 좌우로 45도 이상 돌려 뒤를 바라봅니다.",
                checkPoint = "좌우 회전 각도가 비슷하고 목이나 등에 찌르는 듯한 불편감이 없나요?",
                iconEmoji = "🔄"
            ),
            posture = PostureStage.SITTING
        ),
        SfmaQuestion(
            movement = SfmaMovement(
                id = "sls",
                title = "4. SINGLE LEG STANCE (한 발 서기 균형)",
                category = MovementCategory.SINGLE_LEG_STANCE,
                targetPostures = "QUADRUPED (교차 지지) -> STANDING (한 발 기립)",
                guideDescription = "필요시 벽이나 의자를 손끝으로 가볍게 스치듯 대고 한 발로 10초 이상 안정적으로 섭니다.",
                checkPoint = "골반이 옆으로 기울어지거나 발목이 심하게 흔들리지 않고 중심을 잡을 수 있나요?",
                iconEmoji = "🦩"
            ),
            posture = PostureStage.STANDING
        ),
        SfmaQuestion(
            movement = SfmaMovement(
                id = "squat",
                title = "5. SQUAT (의자 스쿼트 & 하지 복합)",
                category = MovementCategory.SQUAT,
                targetPostures = "SITTING (시트 투 스탠드) -> STANDING (스쿼트)",
                guideDescription = "발을 어깨너비로 벌리고 의자에 앉았다가 손의 반동 없이 허벅지와 둔근 힘으로 일어섭니다.",
                checkPoint = "무릎이 안으로 모이지 않고 고관절과 발목이 부드럽게 굽혀지나요?",
                iconEmoji = "🏋️"
            ),
            posture = PostureStage.STANDING
        )
    )

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    // Map of question index -> Pair(hasPain, isFunctional)
    private val _answers = MutableStateFlow<Map<Int, Pair<Boolean, Boolean>>>(emptyMap())
    val answers: StateFlow<Map<Int, Pair<Boolean, Boolean>>> = _answers.asStateFlow()

    fun answerCurrentQuestion(hasPain: Boolean, isFunctional: Boolean) {
        val updated = _answers.value.toMutableMap()
        updated[_currentQuestionIndex.value] = Pair(hasPain, isFunctional)
        _answers.value = updated

        if (_currentQuestionIndex.value < sfmaQuestions.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            // Assessment Finished!
            calculateResultAndSave()
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    // Result State
    private val _calculatedCharacter = MutableStateFlow(AnimalCharacters.SLOTH)
    val calculatedCharacter: StateFlow<AnimalCharacter> = _calculatedCharacter.asStateFlow()

    private fun calculateResultAndSave() {
        val ans = _answers.value
        val hasAnyPain = ans.values.any { it.first }
        val flexionOk = ans[0]?.second ?: true
        val extensionOk = ans[1]?.second ?: true
        val rotationOk = ans[2]?.second ?: true
        val slsOk = ans[3]?.second ?: true
        val squatOk = ans[4]?.second ?: true

        val mobilityIssue = !flexionOk || !rotationOk
        val stabilityIssue = !extensionOk
        val balanceIssue = !slsOk
        val powerIssue = !squatOk

        val character = AnimalCharacters.determineCharacter(
            hasPain = hasAnyPain,
            mobilityIssue = mobilityIssue,
            stabilityIssue = stabilityIssue,
            balanceIssue = balanceIssue,
            powerIssue = powerIssue
        )
        _calculatedCharacter.value = character

        // Save to DB
        viewModelScope.launch {
            val email = _demographics.value.email.ifBlank { "member@88wellness.com" }
            val entity = AssessmentEntity(
                userEmail = email,
                age = _demographics.value.age,
                gender = _demographics.value.gender,
                laborIntensity = _demographics.value.laborIntensity.label,
                painAreas = _demographics.value.painAreas.joinToString(", "),
                characterId = character.id,
                characterName = character.name,
                mobilityScore = character.mobilityScore,
                stabilityScore = character.stabilityScore,
                balanceScore = character.balanceScore,
                powerScore = character.powerScore,
                notes = character.mbtiCode
            )
            repository.saveAssessment(entity)
        }

        _currentScreen.value = AppScreen.RESULT
    }

    // --- Navigation Flow Functions ---
    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun startSurvey() {
        _currentQuestionIndex.value = 0
        _answers.value = emptyMap()
        _currentScreen.value = AppScreen.SURVEY_DEMOGRAPHICS
    }

    fun proceedToSfmaQuestions() {
        _currentScreen.value = AppScreen.SURVEY_SFMA
    }

    fun proceedToExerciseProgram() {
        // As requested:
        // "운동프로그램으로 넘어가기 위해서는 회원가입,로그인이 필요하게 만들어주고"
        if (currentUser.value != null) {
            _currentScreen.value = AppScreen.ROADMAP
        } else {
            _currentScreen.value = AppScreen.AUTH
        }
    }

    // --- Auth Management ---
    private val _authError = MutableStateFlow<String?>(null)
    val authError: StateFlow<String?> = _authError.asStateFlow()

    fun loginWithKakao() {
        viewModelScope.launch {
            val user = UserEntity(
                userId = "kakao_senior_88",
                email = _demographics.value.email.ifBlank { "kakao_senior@88wellness.com" },
                loginType = "KAKAO",
                name = "카카오 88회원",
                birthDate = "1958-08-08",
                address = "서울특별시 강남구 테헤란로 88"
            )
            repository.saveUser(user)
            _authError.value = null
            _currentScreen.value = AppScreen.ROADMAP
        }
    }

    fun registerWithEmail(
        idInput: String,
        passwordInput: String,
        emailInput: String,
        birthDateInput: String,
        addressInput: String
    ) {
        if (idInput.isBlank() || passwordInput.isBlank() || emailInput.isBlank()) {
            _authError.value = "아이디, 비밀번호, 이메일을 입력해주세요."
            return
        }

        viewModelScope.launch {
            val user = UserEntity(
                userId = idInput.trim(),
                passwordHash = passwordInput,
                email = emailInput.trim(),
                birthDate = birthDateInput.trim(),
                address = addressInput.trim(),
                loginType = "EMAIL",
                name = idInput.trim()
            )
            repository.saveUser(user)
            _authError.value = null
            _currentScreen.value = AppScreen.ROADMAP
        }
    }

    fun loginWithEmail(idOrEmail: String, passwordInput: String) {
        if (idOrEmail.isBlank() || passwordInput.isBlank()) {
            _authError.value = "아이디와 비밀번호를 모두 입력해주세요."
            return
        }
        viewModelScope.launch {
            val user = repository.findUserByUserId(idOrEmail) ?: repository.findUserByEmail(idOrEmail)
            if (user != null) {
                _authError.value = null
                _currentScreen.value = AppScreen.ROADMAP
            } else {
                // If not found, create a demo user session for smooth UX
                val newUser = UserEntity(
                    userId = idOrEmail,
                    passwordHash = passwordInput,
                    email = if (idOrEmail.contains("@")) idOrEmail else "$idOrEmail@wellness88.com",
                    loginType = "EMAIL",
                    name = idOrEmail
                )
                repository.saveUser(newUser)
                _authError.value = null
                _currentScreen.value = AppScreen.ROADMAP
            }
        }
    }

    // --- Workout Execution State ---
    private val _selectedWeekNumber = MutableStateFlow(1)
    val selectedWeekNumber: StateFlow<Int> = _selectedWeekNumber.asStateFlow()

    private val _activeExerciseIndex = MutableStateFlow(0)
    val activeExerciseIndex: StateFlow<Int> = _activeExerciseIndex.asStateFlow()

    private val _timerSeconds = MutableStateFlow(0)
    val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    fun selectWeekAndStartWorkout(weekNumber: Int) {
        _selectedWeekNumber.value = weekNumber
        _activeExerciseIndex.value = 0
        startActiveExercise()
        _currentScreen.value = AppScreen.WORKOUT_PLAYER
    }

    private fun startActiveExercise() {
        val week = ProgramRoadmap.weeks.firstOrNull { it.weekNumber == _selectedWeekNumber.value }
            ?: ProgramRoadmap.weeks.first()
        val exercise = week.exercises.getOrNull(_activeExerciseIndex.value) ?: week.exercises.first()
        _timerSeconds.value = exercise.durationSeconds
        _isTimerRunning.value = true

        speakText("${exercise.title}. 준비되시면 편안한 호흡과 함께 시작합니다. ${exercise.safetyTip}")

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_timerSeconds.value > 0) {
                delay(1000)
                if (_isTimerRunning.value) {
                    _timerSeconds.value -= 1
                    if (_timerSeconds.value == 3) {
                        speakText("3초 전입니다. 마무리 준비하세요.")
                    }
                }
            }
            speakText("동작 완료! 수고하셨습니다. 호흡을 정돈하세요.")
            _isTimerRunning.value = false
        }
    }

    fun toggleTimer() {
        _isTimerRunning.value = !_isTimerRunning.value
    }

    fun nextExerciseOrFinish() {
        val week = ProgramRoadmap.weeks.firstOrNull { it.weekNumber == _selectedWeekNumber.value }
            ?: ProgramRoadmap.weeks.first()
        if (_activeExerciseIndex.value < week.exercises.size - 1) {
            _activeExerciseIndex.value += 1
            startActiveExercise()
        } else {
            // Complete workout
            completeCurrentWorkoutSession()
        }
    }

    fun completeCurrentWorkoutSession() {
        timerJob?.cancel()
        _isTimerRunning.value = false
        val week = ProgramRoadmap.weeks.firstOrNull { it.weekNumber == _selectedWeekNumber.value }
            ?: ProgramRoadmap.weeks.first()

        viewModelScope.launch {
            repository.logCompletedWorkout(
                weekNumber = week.weekNumber,
                dayLabel = "오늘의 맞춤 루틴",
                exerciseTitle = week.title,
                durationSeconds = week.exercises.sumOf { it.durationSeconds }
            )
        }
        speakText("오늘의 88웰니스 운동을 성공적으로 완료하셨습니다! 관절이 한결 가벼워지셨을 거예요.")
        _currentScreen.value = AppScreen.ROADMAP
    }
}
