package com.example.data.repository

import com.example.data.local.AssessmentEntity
import com.example.data.local.UserEntity
import com.example.data.local.WellnessDao
import com.example.data.local.WorkoutLogEntity
import kotlinx.coroutines.flow.Flow

class WellnessRepository(private val dao: WellnessDao) {

    val currentUser: Flow<UserEntity?> = dao.getLatestUser()
    val latestAssessment: Flow<AssessmentEntity?> = dao.getLatestAssessment()
    val allAssessments: Flow<List<AssessmentEntity>> = dao.getAllAssessments()
    val allWorkoutLogs: Flow<List<WorkoutLogEntity>> = dao.getAllWorkoutLogs()

    suspend fun saveUser(user: UserEntity): Long {
        return dao.insertUser(user)
    }

    suspend fun findUserByEmail(email: String): UserEntity? {
        return dao.getUserByEmail(email)
    }

    suspend fun findUserByUserId(userId: String): UserEntity? {
        return dao.getUserByUserId(userId)
    }

    suspend fun saveAssessment(assessment: AssessmentEntity): Long {
        return dao.insertAssessment(assessment)
    }

    suspend fun logCompletedWorkout(
        weekNumber: Int,
        dayLabel: String,
        exerciseTitle: String,
        durationSeconds: Int
    ): Long {
        val log = WorkoutLogEntity(
            weekNumber = weekNumber,
            dayLabel = dayLabel,
            exerciseTitle = exerciseTitle,
            durationSeconds = durationSeconds
        )
        return dao.insertWorkoutLog(log)
    }

    fun getWeekCompletedCount(weekNumber: Int): Flow<Int> {
        return dao.getCompletedCountForWeek(weekNumber)
    }
}
