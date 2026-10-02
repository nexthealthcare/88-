package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val passwordHash: String = "",
    val email: String,
    val birthDate: String = "",
    val address: String = "",
    val loginType: String = "EMAIL", // "KAKAO" or "EMAIL"
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "assessments")
data class AssessmentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userEmail: String,
    val age: Int,
    val gender: String,
    val laborIntensity: String,
    val painAreas: String, // comma separated
    val characterId: String,
    val characterName: String,
    val mobilityScore: Int,
    val stabilityScore: Int,
    val balanceScore: Int,
    val powerScore: Int,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "workout_logs")
data class WorkoutLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weekNumber: Int,
    val dayLabel: String,
    val exerciseTitle: String,
    val durationSeconds: Int,
    val completedAt: Long = System.currentTimeMillis()
)
