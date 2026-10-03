package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dungeons")
data class DungeonEntity(
    @PrimaryKey val id: String,
    val dungeonNumber: Int,
    val title: String,
    val description: String,
    val rankRequired: String,
    val levelRequired: Int,
    val status: String, // "CONQUISTADA", "EN_BATALLA", "BLOQUEADO"
    val progressPercent: Int,
    val completedNodes: Int,
    val totalNodes: Int,
    val estimatedHours: Float,
    val realHours: Float,
    val tags: String, // Comma-separated: Ley de Ohm, Calibre AWG...
    val currentMission: String,
    val rewardTitle: String = ""
)
