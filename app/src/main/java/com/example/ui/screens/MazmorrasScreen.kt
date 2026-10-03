package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.DungeonEntity
import com.example.data.local.UserEntity
import com.example.ui.components.MonarchScreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.HazardRed
import com.example.ui.theme.HazardRedContainer
import com.example.ui.theme.ManaCyan
import com.example.ui.theme.ManaCyanDim
import com.example.ui.theme.OnManaCyan
import com.example.ui.theme.SovereignPurple
import com.example.ui.theme.SovereignPurpleContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceContainerMid
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MonarchViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MazmorrasScreen(
    viewModel: MonarchViewModel,
    userProfile: UserEntity,
    dungeons: List<DungeonEntity>,
    onNavigate: (MonarchScreen) -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedTier by remember { mutableStateOf("activo") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // SYSTEM DIRECTIVE: ORÁCULO DE ASCENSIÓN
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, ManaCyan.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
                .padding(16.dp)
                .testTag("ascension_guide_card")
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(ManaCyanDim),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Terminal,
                                contentDescription = null,
                                tint = OnManaCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "DIRECTIVA DEL SISTEMA // GUÍA MAESTRA",
                            color = ManaCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "SEQ: SEC-047",
                            color = TextSecondary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "¿Por Dónde Empezar? Ruta de Ascensión",
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "“Para dominar el poder del rayo, la termodinámica y los núcleos autómatas, debes purgar cada Mazmorra en estricto orden secuencial. Saltarse una resonancia provocará una sobrecarga letal.”",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                // Presets de Sincronía
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest)
                        .padding(10.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Sincronía de Núcleo Hunter",
                                color = TextSecondary,
                                fontSize = 10.sp
                            )
                            Text(
                                text = when (selectedTier) {
                                    "novato" -> "Nvl. 1 (Iniciado E - Ruta Base)"
                                    "avanzado" -> "Diagnóstico Rápido Activado"
                                    else -> "Nvl. 14 (Operativo D - Mazmorra II)"
                                },
                                color = ManaCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                Triple("novato", "CERO", "Iniciado E"),
                                Triple("activo", "ACTUAL", "Rango D"),
                                Triple("avanzado", "RECALCULAR", "Diagnóstico")
                            ).forEach { (tier, topLabel, bottomLabel) ->
                                val isSelected = selectedTier == tier
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            if (isSelected) ManaCyanDim.copy(alpha = 0.4f) else SurfaceContainerMid
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected) ManaCyan else Color.Transparent,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable { selectedTier = tier }
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = topLabel,
                                            color = if (isSelected) ManaCyan else TextSecondary,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = bottomLabel,
                                            color = if (isSelected) TextPrimary else TextSecondary,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // TÍTULO DEL ÁRBOL
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountTree,
                    contentDescription = null,
                    tint = ManaCyan,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "ÁRBOL DE MAZMORRAS TÉCNICAS",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            Text(
                text = "1 / 5 PURGADAS",
                color = ManaCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // LISTA DE MAZMORRAS (PROGRESIÓN SECUENCIAL)
        // Mazmorra 1: Conquistada
        DungeonCardItem(
            dungeonNumber = "I",
            badgeStatus = "CONQUISTADA",
            rank = "RANGO E",
            title = "Fundamentos y Electricidad Residencial",
            description = "Ley de Ohm, potencia monofásica, calibres AWG, cuadro general con ICG 30mA, PIAs y sistema de puesta a tierra.",
            tags = listOf("Ley de Ohm", "Calibre AWG", "Tableros & Térmicas", "Puesta a Tierra"),
            progressPercent = 100,
            statusType = StatusType.CONQUISTADA,
            reward = "Medalla Chispa Inicial",
            onAction = { onNavigate(MonarchScreen.GRIMORIO) }
        )

        // Mazmorra 2: En Batalla
        DungeonCardItem(
            dungeonNumber = "II",
            badgeStatus = "EN BATALLA • 65% COMPLETADO",
            rank = "RANGO D",
            title = "Electricidad Industrial y Fuerza Motriz",
            description = "Control de máquinas trifásicas 400V, disparo de protecciones, contactores y telemetría de devanados.",
            tags = listOf("Contactores", "Arranque Y-Δ", "Guardamotores", "Telemetría"),
            progressPercent = 65,
            statusType = StatusType.EN_BATALLA,
            subQuests = listOf(
                Pair("Contactores y Enclavamiento", "DOMINADO"),
                Pair("Arranque Estrella-Triángulo (Y-Δ)", "MISIÓN ACTUAL"),
                Pair("Relés Térmicos y Guardamotores", "BLOQUEADO")
            ),
            pace = "~3 días restantes",
            onAction = { onNavigate(MonarchScreen.GRIMORIO) }
        )

        // Mazmorra 3: Sello Arcano
        DungeonCardItem(
            dungeonNumber = "III",
            badgeStatus = "SELLO ARCANO",
            rank = "REQ. NVL 18",
            title = "Termodinámica y Sistemas de Refrigeración",
            description = "Ciclo de compresión de vapor, presostatos diferenciales Danfoss, compresores herméticos y cálculo de sobrecalentamiento.",
            tags = listOf("Compresores Herméticos", "Fluidos R410A / R134a", "Cálculo Sobrecalentamiento"),
            progressPercent = 0,
            statusType = StatusType.BLOQUEADA,
            onAction = { }
        )

        // Mazmorra 4: Sello Arcano
        DungeonCardItem(
            dungeonNumber = "IV",
            badgeStatus = "SELLO ARCANO",
            rank = "REQ. NVL 25",
            title = "Controles Eléctricos y Automatismos",
            description = "Lógica cableada compleja, secuencias de temporizadores on/off delay y telemetría de sensores inductivos.",
            tags = listOf("Lógica Cableada", "Temporizadores", "Sensores Inductivos"),
            progressPercent = 0,
            statusType = StatusType.BLOQUEADA,
            onAction = { }
        )

        // Mazmorra 5: Boss Supremo
        DungeonCardItem(
            dungeonNumber = "BOSS",
            badgeStatus = "MAZMORRA DE JEFE SUPREMO",
            rank = "REQ. NVL 35",
            title = "PLC y Controladores Lógicos Programables",
            description = "Arquitectura Siemens S7-1200 & Allen-Bradley, Lógica Ladder (KOP), Rutinas críticas de seguridad y I/O industriales analógicas.",
            tags = listOf("Siemens S7-1200", "Allen-Bradley", "Ladder KOP", "I/O Analógicas"),
            progressPercent = 0,
            statusType = StatusType.BOSS,
            reward = "Rango de 'Ingeniero Monarca del Sistema'",
            onAction = { }
        )

        // PANEL REAJUSTE DINÁMICO DE CRONOGRAMA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceContainerLow)
                .padding(16.dp)
                .testTag("recalculate_schedule_panel")
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoMode,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Reajuste Dinámico de Cronograma",
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(ElectricBlueContainer.copy(alpha = 0.3f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "IA TÁCTICA",
                            color = ElectricBlue,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "El Sistema recalcula automáticamente las horas de taller y simulaciones requeridas midiendo tus fallos en pruebas de arranque y tiempo de resolución.",
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Horas Invertidas", color = TextSecondary, fontSize = 9.sp)
                            Text("38.5 hrs", color = ManaCyan, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("+4.2 hrs esta semana", color = ManaCyanDim, fontSize = 8.sp)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerLowest)
                            .padding(10.dp)
                    ) {
                        Column {
                            Text("Índice de Retención", color = TextSecondary, fontSize = 9.sp)
                            Text("94.2%", color = SovereignPurple, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("Nivel de Sobrecarga: 0%", color = SovereignPurple, fontSize = 8.sp)
                        }
                    }
                }

                Button(
                    onClick = { viewModel.recalculateSchedule() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("recalculate_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceContainerHigh,
                        contentColor = ElectricBlue
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "EVALUAR PROGRESO Y RECALCULAR RUTA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

enum class StatusType {
    CONQUISTADA,
    EN_BATALLA,
    BLOQUEADA,
    BOSS
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DungeonCardItem(
    dungeonNumber: String,
    badgeStatus: String,
    rank: String,
    title: String,
    description: String,
    tags: List<String>,
    progressPercent: Int,
    statusType: StatusType,
    subQuests: List<Pair<String, String>> = emptyList(),
    pace: String? = null,
    reward: String? = null,
    onAction: () -> Unit
) {
    val isLocked = statusType == StatusType.BLOQUEADA
    val isBoss = statusType == StatusType.BOSS

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dungeon_card_$dungeonNumber"),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Icono / Rango Badge del Nodo
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    when (statusType) {
                        StatusType.CONQUISTADA -> SurfaceContainerHigh
                        StatusType.EN_BATALLA -> ManaCyanDim
                        StatusType.BOSS -> SovereignPurpleContainer
                        StatusType.BLOQUEADA -> SurfaceContainerLowest
                    }
                )
                .border(
                    1.dp,
                    when (statusType) {
                        StatusType.EN_BATALLA -> ManaCyan
                        StatusType.BOSS -> SovereignPurple
                        else -> Color.Transparent
                    },
                    RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            when (statusType) {
                StatusType.CONQUISTADA -> Icon(
                    Icons.Default.Verified,
                    contentDescription = null,
                    tint = ManaCyan,
                    modifier = Modifier.size(24.dp)
                )
                StatusType.EN_BATALLA -> Icon(
                    Icons.Default.Bolt,
                    contentDescription = null,
                    tint = TextPrimary,
                    modifier = Modifier.size(24.dp)
                )
                StatusType.BOSS -> Icon(
                    Icons.Default.Dangerous,
                    contentDescription = null,
                    tint = SovereignPurple,
                    modifier = Modifier.size(24.dp)
                )
                StatusType.BLOQUEADA -> Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Contenido Card
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (statusType == StatusType.EN_BATALLA) SurfaceContainerMid else SurfaceContainerLow
                )
                .border(
                    1.dp,
                    if (statusType == StatusType.EN_BATALLA) ManaCyan.copy(alpha = 0.5f) else Color.Transparent,
                    RoundedCornerShape(12.dp)
                )
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Header Rango
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "MAZMORRA $dungeonNumber • $badgeStatus".uppercase(),
                        color = when (statusType) {
                            StatusType.CONQUISTADA -> ManaCyan
                            StatusType.EN_BATALLA -> ManaCyan
                            StatusType.BOSS -> SovereignPurple
                            StatusType.BLOQUEADA -> MaterialTheme.colorScheme.outline
                        },
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (isLocked) HazardRedContainer.copy(alpha = 0.3f) else SurfaceContainerHigh
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = rank,
                            color = if (isLocked) HazardRed else TextPrimary,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = description,
                    color = TextSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                // Tags de sub-misión
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SurfaceContainerMid)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = tag,
                                color = if (isLocked) MaterialTheme.colorScheme.outline else TextSecondary,
                                fontSize = 9.sp
                            )
                        }
                    }
                }

                // Si está en batalla: barra y subquests
                if (statusType == StatusType.EN_BATALLA) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Progreso de Purgado", color = TextSecondary, fontSize = 10.sp)
                            Text("65% (13/20 Nodos)", color = ManaCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SurfaceContainerLowest)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.65f)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(SovereignPurpleContainer, ElectricBlueContainer, ManaCyan)
                                        )
                                    )
                            )
                        }
                    }

                    // Sub-Quests
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        subQuests.forEach { (subTitle, subStatus) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceContainerLowest)
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = when (subStatus) {
                                            "DOMINADO" -> Icons.Default.CheckCircle
                                            "MISIÓN ACTUAL" -> Icons.Default.RadioButtonChecked
                                            else -> Icons.Default.RadioButtonUnchecked
                                        },
                                        contentDescription = null,
                                        tint = when (subStatus) {
                                            "DOMINADO" -> ManaCyan
                                            "MISIÓN ACTUAL" -> ElectricBlue
                                            else -> MaterialTheme.colorScheme.outlineVariant
                                        },
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = subTitle,
                                        color = TextPrimary,
                                        fontSize = 11.sp
                                    )
                                }
                                Text(
                                    text = subStatus,
                                    color = when (subStatus) {
                                        "DOMINADO" -> ManaCyan
                                        "MISIÓN ACTUAL" -> ElectricBlue
                                        else -> MaterialTheme.colorScheme.outline
                                    },
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Ritmo de combate
                    if (pace != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceContainerLowest)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    Icons.Default.Timelapse,
                                    contentDescription = null,
                                    tint = SovereignPurple,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text("Ritmo de Combate Técnico", color = TextSecondary, fontSize = 10.sp)
                            }
                            Text(pace, color = TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Botón Entrar a Mazmorra Actual
                    Button(
                        onClick = onAction,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("enter_current_dungeon_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ManaCyan,
                            contentColor = OnManaCyan
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.SportsMartialArts,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                "ENTRAR A MAZMORRA ACTUAL",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                // Recompensa si la tiene
                if (reward != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest)
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = SovereignPurple,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(reward, color = SovereignPurple, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                        }
                        if (statusType == StatusType.CONQUISTADA) {
                            Text("100% PURGADA", color = ManaCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
