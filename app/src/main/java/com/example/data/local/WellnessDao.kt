package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WellnessDao {

    // User operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    fun getLatestUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserByUserId(userId: String): UserEntity?

    // Assessment operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: AssessmentEntity): Long

    @Query("SELECT * FROM assessments ORDER BY createdAt DESC LIMIT 1")
    fun getLatestAssessment(): Flow<AssessmentEntity?>

    @Query("SELECT * FROM assessments ORDER BY createdAt DESC")
    fun getAllAssessments(): Flow<List<AssessmentEntity>>

    // Workout logs operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLogEntity): Long

    @Query("SELECT * FROM workout_logs ORDER BY completedAt DESC")
    fun getAllWorkoutLogs(): Flow<List<WorkoutLogEntity>>

    @Query("SELECT COUNT(*) FROM workout_logs WHERE weekNumber = :weekNumber")
    fun getCompletedCountForWeek(weekNumber: Int): Flow<Int>
}
