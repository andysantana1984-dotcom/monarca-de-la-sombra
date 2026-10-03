package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val hunterName: String = "Cazador Jin-Woo Técnico",
    val hunterTitle: String = "Aprendiz Eléctrico de las Sombras",
    val hunterRank: String = "RANGO E",
    val level: Int = 14,
    val xp: Int = 2450,
    val xpNext: Int = 3000,
    val dailyTargetHours: Float = 2.5f,
    val studiedHoursToday: Float = 1.8f,
    val streakDays: Int = 7,
    val notificationsEnabled: Boolean = true,
    val avatarChoice: String = "jinwoo", // "jinwoo", "monarch", "apprentice"
    val avatarCustomUrl: String = "https://lh3.googleusercontent.com/aida-public/AB6AXuBTX4QtwR1QWAeGzF0Qkf1hpDGuDAolMgT228nePTEUZJjrHJojc-bae_9OgtlDIKAXwC9o41TJYWa4MH3IrL73M2A_H7FCM0vVsDl4vNq06PbXtEi_6QI_tzcFQwqWMxSp5DHixBacD3T5fnnFy2RK5BMoqMCsLA1w7PdB1CUUp2aJem4Wgm3uZfFfSfBzzs8N2z4gMfmFPPtwEB7e9-g1m3huqy4Uq-58v7L2fu4j73rcOjaZxc3r",
    val totalHoursStudied: Float = 38.5f,
    val retentionRate: Float = 94.2f
)
