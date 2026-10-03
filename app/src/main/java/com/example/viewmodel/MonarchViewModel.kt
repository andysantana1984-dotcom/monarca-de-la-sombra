package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.ChatMessageEntity
import com.example.data.local.DungeonEntity
import com.example.data.local.UserEntity
import com.example.data.repository.MonarchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MonarchViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MonarchRepository
    val userProfile: StateFlow<UserEntity>
    val dungeons: StateFlow<List<DungeonEntity>>
    val chatMessages: StateFlow<List<ChatMessageEntity>>

    // Countdown study timer state
    private val _timerSecondsLeft = MutableStateFlow(6316) // 1h 45m 16s default
    val timerSecondsLeft: StateFlow<Int> = _timerSecondsLeft.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(true)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private var timerJob: Job? = null

    // System Alert Banner / Toast message
    private val _systemToast = MutableStateFlow<String?>(null)
    val systemToast: StateFlow<String?> = _systemToast.asStateFlow()

    // Grimorio interactive schematic state
    private val _isCurrentFlowing = MutableStateFlow(false)
    val isCurrentFlowing: StateFlow<Boolean> = _isCurrentFlowing.asStateFlow()

    private val _selectedSchematicNode = MutableStateFlow(1)
    val selectedSchematicNode: StateFlow<Int> = _selectedSchematicNode.asStateFlow()

    private val _selectedSchematicCategory = MutableStateFlow("TODOS")
    val selectedSchematicCategory: StateFlow<String> = _selectedSchematicCategory.asStateFlow()

    private val _selectedSchematicId = MutableStateFlow("star_delta")
    val selectedSchematicId: StateFlow<String> = _selectedSchematicId.asStateFlow()

    // AI Monarch Examination State
    private val _currentQuizIndex = MutableStateFlow<Int?>(null)
    val currentQuizIndex: StateFlow<Int?> = _currentQuizIndex.asStateFlow()

    private val _quizFeedback = MutableStateFlow<String?>(null)
    val quizFeedback: StateFlow<String?> = _quizFeedback.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MonarchRepository(database.appDao())

        userProfile = repository.userProfile
            .map { it ?: UserEntity() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = UserEntity()
            )

        dungeons = repository.allDungeons
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

        chatMessages = repository.allMessages
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = emptyList()
            )

        viewModelScope.launch {
            repository.initializeSeedDataIfNeeded()
        }

        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_isTimerRunning.value && _timerSecondsLeft.value > 0) {
                    _timerSecondsLeft.value -= 1
                    if (_timerSecondsLeft.value == 0) {
                        _isTimerRunning.value = false
                        triggerHapticFeedback()
                        showSystemToast("¡MISIÓN DIARIA COMPLETADA! Maná acumulado con éxito.")
                        repository.addXpAndProgress(300, 150, "Misión Diaria Cumplida")
                    }
                }
            }
        }
    }

    fun toggleTimer() {
        _isTimerRunning.value = !_isTimerRunning.value
        triggerHapticFeedback()
        val status = if (_isTimerRunning.value) "Reanudado" else "Pausado"
        showSystemToast("Protocolo de tiempo: $status")
    }

    fun setDailyTargetHours(hours: Float) {
        viewModelScope.launch {
            repository.updateDailyHours(hours)
            _timerSecondsLeft.value = (hours * 3600).toInt()
            showSystemToast("Protocolo diario reconfigurado a $hours Horas")
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleNotifications(enabled)
            val msg = if (enabled) {
                "Alertas del Sistema: Enlace activo a Móvil y PC (Web Push sincronizado)"
            } else {
                "Alertas del Sistema silenciadas temporalmente"
            }
            showSystemToast(msg)
        }
    }

    fun toggleCurrentFlow() {
        _isCurrentFlowing.value = !_isCurrentFlowing.value
        triggerHapticFeedback()
        val text = if (_isCurrentFlowing.value) "Simulación de Flujo 400V Activada" else "Flujo Eléctrico Detenido"
        showSystemToast(text)
    }

    fun selectSchematicNode(node: Int) {
        _selectedSchematicNode.value = node
        triggerHapticFeedback()
    }

    fun selectSchematicCategory(category: String) {
        _selectedSchematicCategory.value = category
    }

    fun selectSchematic(id: String) {
        _selectedSchematicId.value = id
        _selectedSchematicNode.value = 1
    }

    fun recalculateSchedule() {
        viewModelScope.launch {
            val result = repository.recalculateAdaptiveSchedule()
            showSystemToast(result)
        }
    }

    fun askMonarch(prompt: String) {
        val cleanPrompt = prompt.trim()
        if (cleanPrompt.isEmpty()) return

        viewModelScope.launch {
            repository.saveUserMessage(cleanPrompt)

            // Detailed structured pedagogical master response
            val response = generateMonarchExplanation(cleanPrompt)
            delay(600) // slight delay to feel like the sovereign system calculates
            repository.saveMonarchResponse(response.text, response.phase)
            repository.addXpAndProgress(50, 15, "Consulta Técnica con el Monarca")
            showSystemToast("+50 XP por asimilar conocimiento del Monarca")
        }
    }

    private data class MonarchAnswer(val text: String, val phase: String)

    private fun generateMonarchExplanation(query: String): MonarchAnswer {
        val q = query.lowercase()
        return when {
            q.contains("refrig") || q.contains("danfoss") || q.contains("presostato") -> {
                MonarchAnswer(
                    text = "«ATENCIÓN, APRENDIZ.» La refrigeración industrial es el dominio del flujo térmico. Para gobernarla paso a paso:\n\n" +
                            "1. CICLO TERMODINÁMICO DE 4 ELEMENTOS:\n" +
                            "   • Compresor (Eleva presión y temperatura del vapor refrigerante R-410A / R-134a).\n" +
                            "   • Condensador (Disipa calor y convierte gas en líquido a alta presión).\n" +
                            "   • Válvula de Expansión Termostática (Genera caída violenta de presión con expansión isoentálpica).\n" +
                            "   • Evaporador (Absorbe calor de la cámara frigorífica hirviendo el refrigerante).\n\n" +
                            "2. CONTROL CON PRESOSTATO DIFERENCIAL DANFOSS (KP15):\n" +
                            "   • Presostato de Baja (LP): Corta el circuito si la presión cae bajo 1.5 bar (evita congelamiento o fuga).\n" +
                            "   • Presostato de Alta (HP): Disparo de seguridad si supera 24 bar (evita rotura de culatas en compresor).\n" +
                            "   • Lógica Pump-Down: La válvula solenoide corta líquido, el compresor evacúa el evaporador y el presostato de baja apaga el motor automáticamente.",
                    phase = "TERMODINÁMICA Y PRESOSTATOS"
                )
            }
            q.contains("plc") || q.contains("ladder") || q.contains("s7") || q.contains("kop") || q.contains("allen") -> {
                MonarchAnswer(
                    text = "«EL NÚCLEO AUTÓMATA DESPIERTA.» La programación de PLC en lógica Ladder (KOP) requiere orden táctico absoluto:\n\n" +
                            "1. REGLA FUNDAMENTAL DE LECTURA (RUNG):\n" +
                            "   • El escaneo se ejecuta de izquierda a derecha y de arriba a abajo en microsegundos.\n" +
                            "   • Raíl izquierdo (+24VDC) -> Condiciones de entrada (Contactos NA/NC) -> Bobina de salida (Q0.0 / Raíl negativo).\n\n" +
                            "2. TEMPORIZADOR TON (TIMER ON-DELAY):\n" +
                            "   • Parámetro IN: Señal de activación. Debe mantenerse activa.\n" +
                            "   • Parámetro PT: Preset Time (ejemplo: T#5S para los 5 segundos de estrella).\n" +
                            "   • Parámetro Q: Se activa SOLO cuando el tiempo transcurrido (ET) alcanza el Preset (PT).\n\n" +
                            "3. ENTRADAS SINKING VS SOURCING:\n" +
                            "   • Sinking (Módulo receptor NPN): El PLC drena corriente hacia tierra.\n" +
                            "   • Sourcing (Módulo emisor PNP estándar en Europa): El sensor PNP inyecta +24VDC al borne de entrada del PLC.",
                    phase = "LÓGICA AUTÓMATA PLC"
                )
            }
            q.contains("estrella") || q.contains("triángulo") || q.contains("triangulo") || q.contains("motor") || q.contains("y-δ") -> {
                MonarchAnswer(
                    text = "«CIRCUITO DE FUERZA MOTRIZ ANALIZADO.»\n\n" +
                            "• ¿Por qué arrancar en Estrella (Y)?\n" +
                            "En el arranque directo (DOL), un motor trifásico demanda de 6 a 8 veces su corriente nominal (Inom), lo que provocaría caídas de tensión bruscas y disparos magnéticos. En estrella, la tensión en bornes se divide por √3 (aprox. 230V en vez de 400V), reduciendo la corriente a 1/3.\n\n" +
                            "• Secuencia de Contactores Crítica:\n" +
                            "1. Cierran KM1 (Línea) y KM3 (Estrella) a la vez.\n" +
                            "2. Temporizador cuenta de 4 a 6 segundos hasta que el motor alcanza el 80% de su velocidad nominal.\n" +
                            "3. Abre KM3 (Estrella) y se activa el enclavamiento eléctrico cruzado (NC 21-22).\n" +
                            "4. Cierra KM2 (Triángulo) aplicando 400V completos al bobinado.",
                    phase = "ENCLAVAMIENTO INDUSTRIAL"
                )
            }
            q.contains("donde empezar") || q.contains("dónde empezar") || q.contains("empesar") || q.contains("ruta") || q.contains("orden") -> {
                MonarchAnswer(
                    text = "«RUTA DE ASCENSIÓN TÉCNICA DEL MONARCA:»\n\n" +
                            "1º MAZMORRA I (Rango E): Electricidad Residencial y Ley de Ohm. Domina V=I*R, P=V*I, selección de interruptores diferenciales superinmunizados (30mA), curvas magnetotérmicas B, C, D y cálculo de calibres en AWG/mm².\n\n" +
                            "2º MAZMORRA II (Rango D): Fuerza Motriz y Maquinaria Trifásica. Aprende los 400V entre fases, uso de contactores industriales, relés de sobrecarga bimetálicos y guardamotores calibrados al factor de servicio.\n\n" +
                            "3º MAZMORRA III (Rango C): Refrigeración Comercial. Comprensión del diagrama Mollier (entalpía/presión), presostatos diferenciales Danfoss y vacío del circuito frigorífico con bomba de dos etapas.\n\n" +
                            "4º MAZMORRA IV (Rango B): Automatismos Cableados. Temporizadores multifunción, mando bimanual y relés auxiliares de seguridad.\n\n" +
                            "5º MAZMORRA SUPREMA (Rango Monarca): PLC. Lógica Ladder, TIA Portal / RSLogix, direccionamiento de memoria (I, Q, M, DB) y comunicación Profinet/Modbus TCP.",
                    phase = "MAPA MAESTRO"
                )
            }
            else -> {
                MonarchAnswer(
                    text = "«DICTAMEN DEL MONARCA.» He procesado tu inquietud: \"$query\".\n\n" +
                            "En el ejercicio de la ingeniería eléctrica y el control industrial, cada componente responde a leyes físicas inexorables:\n" +
                            "• Nunca intervengas un circuito sin aplicar las 5 Reglas de Oro de Seguridad Eléctrica (Corte efectivo, bloqueo/consignación, verificación de ausencia de tensión, puesta a tierra y señalización).\n" +
                            "• Verifica siempre la tensión nominal de las bobinas (24VDC, 110VAC o 230VAC) antes de energizar tableros.\n" +
                            "• ¿Deseas que profundicemos en el diagrama unifilar, en el cálculo de protecciones o en la lógica de control para este caso?",
                    phase = "DIRECTIVA SOBERANA"
                )
            }
        }
    }

    fun startQuiz() {
        _currentQuizIndex.value = 0
        _quizFeedback.value = null
        showSystemToast("Iniciando Evaluación de Combate Técnico con el Monarca...")
    }

    fun answerQuiz(choiceIndex: Int) {
        val current = _currentQuizIndex.value ?: return
        val isCorrect = when (current) {
            0 -> choiceIndex == 1 // correct option for Q1
            1 -> choiceIndex == 2 // correct option for Q2
            else -> choiceIndex == 0 // correct option for Q3
        }

        if (isCorrect) {
            _quizFeedback.value = "¡EXACTO! Has respondido como un verdadero Maestro Técnico. +150 XP"
            viewModelScope.launch {
                repository.addXpAndProgress(150, 20, "Evaluación Superada")
                repository.advanceDungeonProgress("dungeon_2", 1)
            }
            triggerHapticFeedback()
        } else {
            _quizFeedback.value = "INCORRECTO. El Monarca advierte: Un fallo en este punto provocaría disparo térmico o cortocircuito. Revisa el esquema técnico en el Grimorio."
            triggerHapticFeedback()
        }
    }

    fun nextQuizQuestion() {
        val current = _currentQuizIndex.value ?: return
        if (current < 2) {
            _currentQuizIndex.value = current + 1
            _quizFeedback.value = null
        } else {
            _currentQuizIndex.value = null
            _quizFeedback.value = null
            showSystemToast("Evaluación del Monarca concluida. Datos sincronizados en SQLite.")
        }
    }

    fun shareHunterProfile(context: Context) {
        val user = userProfile.value
        val shareText = "⚡ [SISTEMA DEL MONARCA DE LAS SOMBRAS]\n" +
                "Cazador: ${user.hunterName}\n" +
                "Rango Técnico: ${user.hunterRank} (Nvl. ${user.level})\n" +
                "Título: ${user.hunterTitle}\n" +
                "Horas de Maestría: ${user.totalHoursStudied} hrs\n" +
                "Progreso: Mazmorra de Electricidad Industrial & PLC\n\n" +
                "¡Únete al gremio técnico y asciende de rango!\n" +
                "Enlace de Invocación: https://monarca.sistema.io/hunter/${user.level}"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Rango de Cazador Técnico")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        val chooser = Intent.createChooser(intent, "Compartir Rango con Otros Cazadores")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    fun setAvatar(choice: String) {
        viewModelScope.launch {
            repository.updateAvatar(choice)
            showSystemToast("Avatar actualizado: $choice")
        }
    }

    private fun showSystemToast(msg: String) {
        _systemToast.value = msg
        viewModelScope.launch {
            delay(3500)
            if (_systemToast.value == msg) {
                _systemToast.value = null
            }
        }
    }

    private fun triggerHapticFeedback() {
        try {
            val app = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = app.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = app.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(30)
                }
            }
        } catch (_: Exception) {}
    }
}
