package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CenterInfoScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MatrixExplorerScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.RoadmapScreen
import com.example.ui.screens.SurveyDemographicsScreen
import com.example.ui.screens.SurveySfmaScreen
import com.example.ui.screens.WellnessBottomNavigation
import com.example.ui.screens.WellnessTopBar
import com.example.ui.screens.WorkoutPlayerScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.WellnessViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                WellnessAppRoot()
            }
        }
    }
}

@Composable
fun WellnessAppRoot(viewModel: WellnessViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val isLargeFontMode by viewModel.isLargeFontMode.collectAsStateWithLifecycle()

    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val latestAssessment by viewModel.latestAssessment.collectAsStateWithLifecycle()
    val workoutLogs by viewModel.workoutLogs.collectAsStateWithLifecycle()

    val demographics by viewModel.demographics.collectAsStateWithLifecycle()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsStateWithLifecycle()
    val calculatedCharacter by viewModel.calculatedCharacter.collectAsStateWithLifecycle()
    val authError by viewModel.authError.collectAsStateWithLifecycle()

    val selectedWeekNumber by viewModel.selectedWeekNumber.collectAsStateWithLifecycle()
    val activeExerciseIndex by viewModel.activeExerciseIndex.collectAsStateWithLifecycle()
    val timerSeconds by viewModel.timerSeconds.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()

    // Android back handling
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        when (currentScreen) {
            AppScreen.SURVEY_SFMA -> viewModel.previousQuestion()
            AppScreen.SURVEY_DEMOGRAPHICS -> viewModel.navigateTo(AppScreen.HOME)
            AppScreen.RESULT -> viewModel.navigateTo(AppScreen.HOME)
            AppScreen.AUTH -> viewModel.navigateTo(AppScreen.RESULT)
            AppScreen.WORKOUT_PLAYER -> viewModel.navigateTo(AppScreen.ROADMAP)
            AppScreen.ROADMAP, AppScreen.MATRIX_EXPLORER, AppScreen.CENTER_INFO -> viewModel.navigateTo(AppScreen.HOME)
            else -> viewModel.navigateTo(AppScreen.HOME)
        }
    }

    val topBarTitle = when (currentScreen) {
        AppScreen.HOME -> "88웰니스"
        AppScreen.SURVEY_DEMOGRAPHICS -> "기본 건강 설문"
        AppScreen.SURVEY_SFMA -> "88웰니스 바디체크"
        AppScreen.RESULT -> "신체 MBTI 결과"
        AppScreen.AUTH -> "회원가입 / 로그인"
        AppScreen.ROADMAP -> "12주 운동처방 로드맵"
        AppScreen.MATRIX_EXPLORER -> "4×4 매트릭스 탐색기"
        AppScreen.WORKOUT_PLAYER -> "시니어 가이드 운동"
        AppScreen.CENTER_INFO -> "88 오프라인 센터"
    }

    val showBottomBar = currentScreen in listOf(
        AppScreen.HOME,
        AppScreen.ROADMAP,
        AppScreen.MATRIX_EXPLORER,
        AppScreen.CENTER_INFO
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            WellnessTopBar(
                title = topBarTitle,
                canNavigateBack = currentScreen != AppScreen.HOME,
                isLargeFontMode = isLargeFontMode,
                onBackClick = {
                    when (currentScreen) {
                        AppScreen.SURVEY_SFMA -> viewModel.previousQuestion()
                        AppScreen.SURVEY_DEMOGRAPHICS -> viewModel.navigateTo(AppScreen.HOME)
                        AppScreen.RESULT -> viewModel.navigateTo(AppScreen.HOME)
                        AppScreen.AUTH -> viewModel.navigateTo(AppScreen.RESULT)
                        AppScreen.WORKOUT_PLAYER -> viewModel.navigateTo(AppScreen.ROADMAP)
                        else -> viewModel.navigateTo(AppScreen.HOME)
                    }
                },
                onToggleLargeFont = { viewModel.toggleLargeFontMode() }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                WellnessBottomNavigation(
                    currentScreen = currentScreen,
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        currentUser = currentUser,
                        latestAssessment = latestAssessment,
                        isLargeFontMode = isLargeFontMode,
                        onStartSurvey = { viewModel.startSurvey() },
                        onNavigate = { screen -> viewModel.navigateTo(screen) }
                    )
                }

                AppScreen.SURVEY_DEMOGRAPHICS -> {
                    SurveyDemographicsScreen(
                        demographics = demographics,
                        isLargeFontMode = isLargeFontMode,
                        onUpdateAge = { viewModel.updateAge(it) },
                        onUpdateGender = { viewModel.updateGender(it) },
                        onUpdateEmail = { viewModel.updateEmail(it) },
                        onUpdateLaborIntensity = { viewModel.updateLaborIntensity(it) },
                        onTogglePainArea = { viewModel.togglePainArea(it) },
                        onUpdateExerciseFrequency = { viewModel.updateExerciseFrequency(it) },
                        onNext = { viewModel.proceedToSfmaQuestions() }
                    )
                }

                AppScreen.SURVEY_SFMA -> {
                    val q = viewModel.sfmaQuestions[currentQuestionIndex]
                    SurveySfmaScreen(
                        currentQuestion = q,
                        currentIndex = currentQuestionIndex,
                        totalQuestions = viewModel.sfmaQuestions.size,
                        isLargeFontMode = isLargeFontMode,
                        onAnswer = { hasPain, isFunctional ->
                            viewModel.answerCurrentQuestion(hasPain, isFunctional)
                        },
                        onPrevious = { viewModel.previousQuestion() },
                        onSpeak = { text -> viewModel.speakText(text) }
                    )
                }

                AppScreen.RESULT -> {
                    ResultScreen(
                        character = calculatedCharacter,
                        isLargeFontMode = isLargeFontMode,
                        onProceedToWorkout = { viewModel.proceedToExerciseProgram() },
                        onOpenMatrix = { viewModel.navigateTo(AppScreen.MATRIX_EXPLORER) }
                    )
                }

                AppScreen.AUTH -> {
                    AuthScreen(
                        initialEmail = demographics.email,
                        authError = authError,
                        isLargeFontMode = isLargeFontMode,
                        onLoginWithKakao = { viewModel.loginWithKakao() },
                        onRegisterWithEmail = { id, pass, email, birth, addr ->
                            viewModel.registerWithEmail(id, pass, email, birth, addr)
                        },
                        onLoginWithEmail = { idOrEmail, pass ->
                            viewModel.loginWithEmail(idOrEmail, pass)
                        },
                        onGuestProceed = { viewModel.navigateTo(AppScreen.ROADMAP) }
                    )
                }

                AppScreen.ROADMAP -> {
                    RoadmapScreen(
                        workoutLogs = workoutLogs,
                        isLargeFontMode = isLargeFontMode,
                        onStartWorkoutForWeek = { weekNum ->
                            viewModel.selectWeekAndStartWorkout(weekNum)
                        },
                        onStartEvaluation = { viewModel.startSurvey() }
                    )
                }

                AppScreen.MATRIX_EXPLORER -> {
                    MatrixExplorerScreen(
                        isLargeFontMode = isLargeFontMode,
                        onSpeak = { text -> viewModel.speakText(text) }
                    )
                }

                AppScreen.WORKOUT_PLAYER -> {
                    WorkoutPlayerScreen(
                        selectedWeekNumber = selectedWeekNumber,
                        activeExerciseIndex = activeExerciseIndex,
                        timerSeconds = timerSeconds,
                        isTimerRunning = isTimerRunning,
                        isLargeFontMode = isLargeFontMode,
                        onToggleTimer = { viewModel.toggleTimer() },
                        onNextOrFinish = { viewModel.nextExerciseOrFinish() },
                        onSpeak = { text -> viewModel.speakText(text) }
                    )
                }

                AppScreen.CENTER_INFO -> {
                    CenterInfoScreen(isLargeFontMode = isLargeFontMode)
                }
            }
        }
    }
}
