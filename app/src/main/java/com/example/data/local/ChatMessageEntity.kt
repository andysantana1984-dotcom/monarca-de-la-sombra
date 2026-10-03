package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sender: String, // "MONARCH", "USER"
    val senderName: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val stepPhase: String? = null // e.g. "FASE 1", "DICTAMEN"
)

@Entity(tableName = "study_logs")
data class StudyLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val minutes: Int,
    val topic: String,
    val timestamp: Long = System.currentTimeMillis()
)
