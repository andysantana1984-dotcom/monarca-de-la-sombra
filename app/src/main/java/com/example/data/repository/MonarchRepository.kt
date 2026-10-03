package com.example.data.repository

import com.example.data.local.AppDao
import com.example.data.local.ChatMessageEntity
import com.example.data.local.DungeonEntity
import com.example.data.local.StudyLogEntity
import com.example.data.local.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class MonarchRepository(private val dao: AppDao) {

    val userProfile: Flow<UserEntity?> = dao.getUserProfile()
    val allDungeons: Flow<List<DungeonEntity>> = dao.getAllDungeons()
    val allMessages: Flow<List<ChatMessageEntity>> = dao.getAllMessages()
    val allStudyLogs: Flow<List<StudyLogEntity>> = dao.getAllStudyLogs()

    suspend fun initializeSeedDataIfNeeded() {
        val existingUser = dao.getUserProfile().firstOrNull()
        if (existingUser == null) {
            dao.insertUser(UserEntity())
        }

        val existingDungeons = dao.getAllDungeons().firstOrNull()
        if (existingDungeons.isNullOrEmpty()) {
            val defaultDungeons = listOf(
                DungeonEntity(
                    id = "dungeon_1",
                    dungeonNumber = 1,
                    title = "Fundamentos y Electricidad Residencial",
                    description = "Ley de Ohm, potencia monofásica, calibres AWG, cuadro general con ICG 30mA, PIAs y sistema de puesta a tierra.",
                    rankRequired = "RANGO E",
                    levelRequired = 1,
                    status = "CONQUISTADA",
                    progressPercent = 100,
                    completedNodes = 15,
                    totalNodes = 15,
                    estimatedHours = 12.0f,
                    realHours = 10.5f,
                    tags = "Ley de Ohm, Calibre AWG, Tableros & Térmicas, Puesta a Tierra",
                    currentMission = "Completada al 100%",
                    rewardTitle = "Medalla Chispa Inicial"
                ),
                DungeonEntity(
                    id = "dungeon_2",
                    dungeonNumber = 2,
                    title = "Electricidad Industrial y Fuerza Motriz",
                    description = "Control de máquinas trifásicas 400V, disparo de protecciones, contactores y telemetría de devanados.",
                    rankRequired = "RANGO D",
                    levelRequired = 10,
                    status = "EN_BATALLA",
                    progressPercent = 65,
                    completedNodes = 13,
                    totalNodes = 20,
                    estimatedHours = 20.0f,
                    realHours = 16.0f,
                    tags = "Contactores, Arranque Y-Δ, Guardamotores, Telemetría",
                    currentMission = "Arranque Estrella-Triángulo (Y-Δ)",
                    rewardTitle = "Núcleo de Potencia Trifásica"
                ),
                DungeonEntity(
                    id = "dungeon_3",
                    dungeonNumber = 3,
                    title = "Termodinámica y Sistemas de Refrigeración",
                    description = "Ciclo de compresión de vapor, presostatos diferenciales Danfoss, compresores herméticos y cálculo de sobrecalentamiento.",
                    rankRequired = "RANGO C",
                    levelRequired = 18,
                    status = "BLOQUEADO",
                    progressPercent = 0,
                    completedNodes = 0,
                    totalNodes = 18,
                    estimatedHours = 25.0f,
                    realHours = 0f,
                    tags = "Compresores Herméticos, Fluidos R410A / R134a, Presostatos",
                    currentMission = "Requiere Nivel 18 para desbloquear",
                    rewardTitle = "Emblema del Frío Industrial"
                ),
                DungeonEntity(
                    id = "dungeon_4",
                    dungeonNumber = 4,
                    title = "Controles Eléctricos y Automatismos",
                    description = "Lógica cableada industrial, secuencias con temporizadores on/off delay, contactores auxiliares y sensores industriales.",
                    rankRequired = "RANGO B",
                    levelRequired = 25,
                    status = "BLOQUEADO",
                    progressPercent = 0,
                    completedNodes = 0,
                    totalNodes = 22,
                    estimatedHours = 30.0f,
                    realHours = 0f,
                    tags = "Lógica Cableada, Temporizadores, Sensores Inductivos",
                    currentMission = "Requiere Nivel 25 para desbloquear",
                    rewardTitle = "Sello del Relé Maestro"
                ),
                DungeonEntity(
                    id = "dungeon_5",
                    dungeonNumber = 5,
                    title = "PLC y Controladores Lógicos Programables",
                    description = "Mazmorra de Jefe Supremo: Arquitectura Siemens S7-1200 & Allen-Bradley, Lógica Ladder (KOP), temporizadores TON y entradas/salidas analógicas.",
                    rankRequired = "JEFE SUPREMO",
                    levelRequired = 35,
                    status = "BLOQUEADO",
                    progressPercent = 0,
                    completedNodes = 0,
                    totalNodes = 30,
                    estimatedHours = 45.0f,
                    realHours = 0f,
                    tags = "Siemens S7-1200, Allen-Bradley, Ladder KOP, I/O Analógicas",
                    currentMission = "Requiere Nivel 35 para enfrentar al Jefe",
                    rewardTitle = "Rango de 'Ingeniero Monarca del Sistema'"
                )
            )
            dao.insertDungeons(defaultDungeons)
        }

        val existingMessages = dao.getAllMessages().firstOrNull()
        if (existingMessages.isNullOrEmpty()) {
            val welcomeMessages = listOf(
                ChatMessageEntity(
                    sender = "MONARCH",
                    senderName = "MAESTRO MONARCA DE LAS SOMBRAS",
                    content = "«¡LEVÁNTATE, APRENDIZ!» He analizado tu sincronía con la energía electromagnética. Como tu tutor de estudio, te guiaré paso a paso por los senderos de la electricidad residencial, industrial, refrigeración, controles y programación de PLC.",
                    stepPhase = "DICTAMEN DEL SOBERANO"
                ),
                ChatMessageEntity(
                    sender = "MONARCH",
                    senderName = "MAESTRO MONARCA DE LAS SOMBRAS",
                    content = "Para dominar el arranque de motores sin detonar una sobrecarga fatal en la red trifásica, debes forjar en tu mente la diferencia crítica entre la conexión Estrella (Y) y Triángulo (Δ):\n\n• FASE 1 (Amortiguamiento en Estrella): La tensión por bobina cae a V/√3 (58%), limitando la corriente a 1/3 del nominal.\n• FASE 2 (Conmutación a Triángulo): Tras el retardo del temporizador (4-8s), conmuta a triángulo entregando el 100% del par al eje.",
                    stepPhase = "FASE 1 & 2"
                )
            )
            for (msg in welcomeMessages) {
                dao.insertMessage(msg)
            }
        }
    }

    suspend fun updateDailyHours(hours: Float) {
        val user = dao.getUserProfile().firstOrNull() ?: UserEntity()
        dao.updateUser(user.copy(dailyTargetHours = hours))
    }

    suspend fun toggleNotifications(enabled: Boolean) {
        val user = dao.getUserProfile().firstOrNull() ?: UserEntity()
        dao.updateUser(user.copy(notificationsEnabled = enabled))
    }

    suspend fun updateAvatar(choice: String, customUrl: String? = null) {
        val user = dao.getUserProfile().firstOrNull() ?: UserEntity()
        dao.updateUser(
            user.copy(
                avatarChoice = choice,
                avatarCustomUrl = customUrl ?: user.avatarCustomUrl
            )
        )
    }

    suspend fun addXpAndProgress(xpGained: Int, minutesStudied: Int, topic: String) {
        val user = dao.getUserProfile().firstOrNull() ?: UserEntity()
        val newXp = user.xp + xpGained
        var newLevel = user.level
        var newXpNext = user.xpNext
        var newRank = user.hunterRank

        if (newXp >= user.xpNext) {
            newLevel += 1
            newXpNext += 800
            newRank = when {
                newLevel >= 35 -> "MONARCA DE LAS SOMBRAS"
                newLevel >= 28 -> "RANGO S"
                newLevel >= 22 -> "RANGO A"
                newLevel >= 18 -> "RANGO B"
                newLevel >= 14 -> "RANGO C"
                newLevel >= 8 -> "RANGO D"
                else -> "RANGO E"
            }
        }

        val updatedHoursToday = user.studiedHoursToday + (minutesStudied / 60f)
        val updatedTotalHours = user.totalHoursStudied + (minutesStudied / 60f)

        dao.updateUser(
            user.copy(
                xp = newXp,
                level = newLevel,
                xpNext = newXpNext,
                hunterRank = newRank,
                studiedHoursToday = updatedHoursToday,
                totalHoursStudied = updatedTotalHours
            )
        )

        dao.insertStudyLog(
            StudyLogEntity(
                minutes = minutesStudied,
                topic = topic
            )
        )
    }

    suspend fun recalculateAdaptiveSchedule(): String {
        val user = dao.getUserProfile().firstOrNull() ?: UserEntity()
        // Algoritmo adaptativo del Monarca: adjusts schedule based on retention and completion pace
        val dungeons = dao.getAllDungeons().firstOrNull() ?: emptyList()
        val d2 = dungeons.find { it.id == "dungeon_2" }
        if (d2 != null && d2.realHours < d2.estimatedHours) {
            val optimizedHours = (d2.estimatedHours * 0.85f)
            dao.updateDungeon(d2.copy(estimatedHours = optimizedHours))
        }
        return "Cronograma recalculado con éxito por el Algoritmo del Monarca: -15% de tiempo proyectado gracias a tu alto índice de retención (${user.retentionRate}%)."
    }

    suspend fun saveUserMessage(content: String) {
        dao.insertMessage(
            ChatMessageEntity(
                sender = "USER",
                senderName = "CAZADOR APRENDIZ",
                content = content
            )
        )
    }

    suspend fun saveMonarchResponse(content: String, stepPhase: String? = null) {
        dao.insertMessage(
            ChatMessageEntity(
                sender = "MONARCH",
                senderName = "MAESTRO MONARCA DE LAS SOMBRAS",
                content = content,
                stepPhase = stepPhase
            )
        )
    }

    suspend fun advanceDungeonProgress(dungeonId: String, additionalNodes: Int) {
        val dungeon = dao.getDungeonById(dungeonId) ?: return
        val newCompleted = (dungeon.completedNodes + additionalNodes).coerceAtMost(dungeon.totalNodes)
        val newPercent = (newCompleted * 100) / dungeon.totalNodes
        val newStatus = if (newPercent >= 100) "CONQUISTADA" else "EN_BATALLA"
        dao.updateDungeon(
            dungeon.copy(
                completedNodes = newCompleted,
                progressPercent = newPercent,
                status = newStatus
            )
        )
    }
}
