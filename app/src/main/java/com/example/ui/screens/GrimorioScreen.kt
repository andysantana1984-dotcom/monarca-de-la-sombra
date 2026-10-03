package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.ElectricMeter
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MonarchScreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.HazardRed
import com.example.ui.theme.ManaCyan
import com.example.ui.theme.ManaCyanDim
import com.example.ui.theme.SovereignPurple
import com.example.ui.theme.SovereignPurpleContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceContainerMid
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MonarchViewModel

@Composable
fun GrimorioScreen(
    viewModel: MonarchViewModel,
    isCurrentFlowing: Boolean,
    selectedNode: Int,
    selectedCategory: String,
    onNavigate: (MonarchScreen) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Animación de flujo continuo de corriente
    val infiniteTransition = rememberInfiniteTransition(label = "mana_current_anim")
    val flowPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "flow_dash_phase"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // HUD STATUS & FILTROS
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainerLow)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = null,
                        tint = ManaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Column {
                        Text(
                            text = "REGISTRO TÉCNICO // GRIMORIO V4.2",
                            color = ManaCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Simbología IEC 60617 & NEMA ICS 19-2002",
                            color = TextSecondary,
                            fontSize = 8.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "SINCRONIZADO",
                        color = TextPrimary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Category Filter Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    Pair("TODOS", Icons.Default.FolderOpen),
                    Pair("RESIDENCIAL", Icons.Default.HomeRepairService),
                    Pair("CONTROLES", Icons.Default.ElectricMeter),
                    Pair("PLC", Icons.Default.Memory)
                ).forEach { (cat, icon) ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) ElectricBlueContainer else SurfaceContainerHigh)
                            .clickable { viewModel.selectSchematicCategory(cat) }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) TextPrimary else TextSecondary,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = cat,
                                color = if (isSelected) TextPrimary else TextSecondary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // ESQUEMA PRINCIPAL DESTACADO: ARRANQUE ESTRELLA-TRIÁNGULO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, ManaCyan.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                .testTag("schematic_star_delta_card")
        ) {
            Column {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = ManaCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "DIAGRAMA DEL DÍA // RANGO A",
                                color = ManaCyan,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Arranque Estrella-Triángulo Trifásico",
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SovereignPurpleContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "MOTOR 400V",
                            color = SovereignPurple,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Control Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerMid)
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { viewModel.toggleCurrentFlow() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCurrentFlowing) ManaCyanDim else ElectricBlueContainer,
                                contentColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(30.dp).testTag("toggle_flow_button")
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isCurrentFlowing) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = if (isCurrentFlowing) "PAUSAR FLUJO" else "SIMULAR FLUJO VIVO",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.selectSchematicNode(1) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceContainerHigh,
                                contentColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                                Text("REINICIAR", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(Icons.Default.Fullscreen, contentDescription = "Ampliar", tint = TextPrimary, modifier = Modifier.size(18.dp))
                    }
                }

                // VISOR VECTORIAL SCHEMATIC CANVAS (Jetpack Compose Canvas)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(SurfaceContainerLowest)
                        .padding(12.dp)
                        .testTag("schematic_canvas_box")
                ) {
                    val outlineColor = MaterialTheme.colorScheme.outline
                    val outlineVariantColor = MaterialTheme.colorScheme.outlineVariant

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height

                        // Buses Principales Trifásicos L1, L2, L3 + PE
                        val busY1 = h * 0.10f
                        val busY2 = h * 0.14f
                        val busY3 = h * 0.18f
                        val busYPE = h * 0.22f

                        // Líneas de bus
                        drawLine(ManaCyan, Offset(20f, busY1), Offset(w - 20f, busY1), strokeWidth = 2.5f)
                        drawLine(ElectricBlue, Offset(20f, busY2), Offset(w - 20f, busY2), strokeWidth = 2.5f)
                        drawLine(SovereignPurple, Offset(20f, busY3), Offset(w - 20f, busY3), strokeWidth = 2.5f)
                        drawLine(
                            Color(0xFF3198DC),
                            Offset(20f, busYPE),
                            Offset(w - 20f, busYPE),
                            strokeWidth = 1.5f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
                        )

                        // Guardamotor Magnetotérmico Q1 (25A)
                        val q1Left = w * 0.18f
                        val q1Top = h * 0.28f
                        val q1Width = w * 0.25f
                        val q1Height = h * 0.12f

                        drawRoundRect(
                            color = if (selectedNode == 1) ManaCyanDim else SurfaceContainerMid,
                            topLeft = Offset(q1Left, q1Top),
                            size = Size(q1Width, q1Height),
                            cornerRadius = CornerRadius(6f, 6f)
                        )
                        drawRoundRect(
                            color = if (selectedNode == 1) ManaCyan else outlineVariantColor,
                            topLeft = Offset(q1Left, q1Top),
                            size = Size(q1Width, q1Height),
                            cornerRadius = CornerRadius(6f, 6f),
                            style = Stroke(width = 2f)
                        )

                        // Contactor Principal KM1 (Línea)
                        val km1Top = h * 0.44f
                        drawRoundRect(
                            color = if (selectedNode == 2) ManaCyanDim else SurfaceContainerMid,
                            topLeft = Offset(q1Left, km1Top),
                            size = Size(q1Width, q1Height),
                            cornerRadius = CornerRadius(6f, 6f)
                        )
                        drawRoundRect(
                            color = if (selectedNode == 2) ManaCyan else outlineVariantColor,
                            topLeft = Offset(q1Left, km1Top),
                            size = Size(q1Width, q1Height),
                            cornerRadius = CornerRadius(6f, 6f),
                            style = Stroke(width = 2f)
                        )

                        // Relé Térmico F1
                        val f1Top = h * 0.60f
                        val f1Height = h * 0.08f
                        drawRoundRect(
                            color = SurfaceContainerHigh,
                            topLeft = Offset(q1Left + 8f, f1Top),
                            size = Size(q1Width - 16f, f1Height),
                            cornerRadius = CornerRadius(4f, 4f),
                            style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 4f)))
                        )

                        // Motor Asíncrono Trifásico M 3~ (Círculo)
                        val motorCenter = Offset(q1Left + q1Width / 2f, h * 0.82f)
                        val motorRadius = h * 0.12f
                        drawCircle(
                            color = SurfaceContainerMid,
                            center = motorCenter,
                            radius = motorRadius
                        )
                        drawCircle(
                            color = ManaCyan,
                            center = motorCenter,
                            radius = motorRadius,
                            style = Stroke(width = 2.5f)
                        )

                        // Contactor KM2 (Triángulo)
                        val km2Left = w * 0.48f
                        drawRoundRect(
                            color = if (selectedNode == 3) SovereignPurpleContainer else SurfaceContainerMid,
                            topLeft = Offset(km2Left, km1Top),
                            size = Size(q1Width, q1Height),
                            cornerRadius = CornerRadius(6f, 6f)
                        )
                        drawRoundRect(
                            color = if (selectedNode == 3) SovereignPurple else outlineVariantColor,
                            topLeft = Offset(km2Left, km1Top),
                            size = Size(q1Width, q1Height),
                            cornerRadius = CornerRadius(6f, 6f),
                            style = Stroke(width = 2f)
                        )

                        // Contactor KM3 (Estrella)
                        val km3Left = w * 0.75f
                        drawRoundRect(
                            color = if (selectedNode == 3) SovereignPurpleContainer else SurfaceContainerMid,
                            topLeft = Offset(km3Left, km1Top),
                            size = Size(q1Width * 0.85f, q1Height),
                            cornerRadius = CornerRadius(6f, 6f)
                        )
                        drawRoundRect(
                            color = if (selectedNode == 3) SovereignPurple else outlineVariantColor,
                            topLeft = Offset(km3Left, km1Top),
                            size = Size(q1Width * 0.85f, q1Height),
                            cornerRadius = CornerRadius(6f, 6f),
                            style = Stroke(width = 2f)
                        )

                        // Puente Estrella sobre KM3
                        drawLine(
                            ManaCyan,
                            Offset(km3Left + 8f, km1Top - 10f),
                            Offset(km3Left + q1Width * 0.85f - 8f, km1Top - 10f),
                            strokeWidth = 2.5f
                        )

                        // Cableado principal
                        drawLine(outlineColor, Offset(q1Left + q1Width * 0.3f, busY3), Offset(q1Left + q1Width * 0.3f, q1Top), strokeWidth = 2f)
                        drawLine(outlineColor, Offset(q1Left + q1Width * 0.5f, busY3), Offset(q1Left + q1Width * 0.5f, q1Top), strokeWidth = 2f)
                        drawLine(outlineColor, Offset(q1Left + q1Width * 0.7f, busY3), Offset(q1Left + q1Width * 0.7f, q1Top), strokeWidth = 2f)

                        drawLine(outlineColor, Offset(q1Left + q1Width / 2f, q1Top + q1Height), Offset(q1Left + q1Width / 2f, km1Top), strokeWidth = 2f)
                        drawLine(outlineColor, Offset(q1Left + q1Width / 2f, km1Top + q1Height), Offset(q1Left + q1Width / 2f, f1Top), strokeWidth = 2f)
                        drawLine(outlineColor, Offset(q1Left + q1Width / 2f, f1Top + f1Height), Offset(motorCenter.x, motorCenter.y - motorRadius), strokeWidth = 2f)

                        // Puentes KM2 & KM3 a devanados
                        drawLine(outlineColor, Offset(motorCenter.x + motorRadius * 0.8f, motorCenter.y), Offset(km2Left + q1Width / 2f, motorCenter.y), strokeWidth = 2f)
                        drawLine(outlineColor, Offset(km2Left + q1Width / 2f, motorCenter.y), Offset(km2Left + q1Width / 2f, km1Top + q1Height), strokeWidth = 2f)

                        // Animación de corriente fluida si isCurrentFlowing
                        if (isCurrentFlowing) {
                            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), flowPhase)
                            drawLine(
                                color = ManaCyan,
                                start = Offset(q1Left + q1Width / 2f, busY1),
                                end = Offset(motorCenter.x, motorCenter.y - motorRadius),
                                strokeWidth = 3f,
                                pathEffect = pathEffect
                            )
                        }
                    }

                    // Etiquetas Superpuestas
                    Text(
                        text = "L1 L2 L3 PE (400V)",
                        color = ManaCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.TopStart).padding(start = 6.dp)
                    )

                    Text(
                        text = "Q1 25A\nGUARDAMOTOR",
                        color = TextPrimary,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 56.dp, bottom = 48.dp)
                    )

                    Text(
                        text = "KM1 LÍNEA",
                        color = ManaCyan,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 64.dp, top = 20.dp)
                    )

                    Text(
                        text = "M 3~\nMOTOR",
                        color = TextPrimary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart).padding(start = 72.dp, bottom = 26.dp)
                    )

                    Text(
                        text = "KM2 Δ\nTRIÁNGULO",
                        color = SovereignPurple,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center).padding(start = 30.dp, top = 20.dp)
                    )

                    Text(
                        text = "KM3 Y\nESTRELLA",
                        color = ElectricBlue,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp, top = 20.dp)
                    )

                    // Overlay de estado de flujo
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "MODO IEC: Vpk 565V",
                            color = ManaCyan,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isCurrentFlowing) "FLUJO: 400V 50Hz EN VIVO" else "FLUJO: DETENIDO",
                            color = if (isCurrentFlowing) ManaCyan else TextSecondary,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // LEYENDA DIAGNÓSTICA TÁCTIL (SELECTOR DE NODOS 1, 2, 3)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerMid)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "LEYENDA DIAGNÓSTICA TÁCTIL (PULSA UN NODO):",
                            color = TextSecondary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "3 PUNTOS CLAVE",
                            color = ManaCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Nodo 1
                    DiagnosticNodeRow(
                        nodeNumber = 1,
                        isSelected = selectedNode == 1,
                        title = "Nodo 1: Disyuntor Magnetotérmico Q1 y Parada de Emergencia",
                        description = "Protección magnética contra cortocircuito (Icu=50kA) y corte bi-polar de la maniobra de control ante sobreintensidad.",
                        badgeColor = ElectricBlueContainer,
                        onClick = { viewModel.selectSchematicNode(1) }
                    )

                    // Nodo 2
                    DiagnosticNodeRow(
                        nodeNumber = 2,
                        isSelected = selectedNode == 2,
                        title = "Nodo 2: Bobina KM1 y Autoenclavamiento 13-14",
                        description = "Alimentación 230VAC (A1-A2). Cierra los tres polos de potencia simultáneamente suministrando corriente a los devanados U1, V1, W1.",
                        badgeColor = ManaCyanDim,
                        onClick = { viewModel.selectSchematicNode(2) }
                    )

                    // Nodo 3
                    DiagnosticNodeRow(
                        nodeNumber = 3,
                        isSelected = selectedNode == 3,
                        title = "Nodo 3: Enclavamiento Cruzado NC (Interlock KM2/KM3)",
                        description = "Evita cortocircuito bifásico violento. El contacto NC 21-22 de KM3 impide físicamente que KM2 cierre mientras la estrella esté activa.",
                        badgeColor = SovereignPurpleContainer,
                        onClick = { viewModel.selectSchematicNode(3) }
                    )
                }
            }
        }

        // ESQUEMA SECUNDARIO: LÓGICA LADDER (ESCALERA) PARA PLC
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, SovereignPurple.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                .testTag("ladder_schematic_card")
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountTree,
                            contentDescription = null,
                            tint = SovereignPurple,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "PROGRAMACIÓN INDUSTRIAL PLC",
                                color = SovereignPurple,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Diagrama Ladder: Conmutación Estrella/Triángulo",
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(SurfaceContainerMid)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "S7-1200 / TIA",
                            color = ManaCyan,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // PELDAÑO 000
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerMid)
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "RUNG 000 // MARCHA & ENCLAVAMIENTO (KM1)",
                                    color = ManaCyan,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "LÓGICA BOOLEANA",
                                    color = TextSecondary,
                                    fontSize = 8.sp
                                )
                            }

                            // Visual Ladder Rung
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceContainerLowest)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // Raíl +24V
                                    Box(modifier = Modifier.width(3.dp).height(40.dp).background(ManaCyan))
                                    // Contacto I0.0 (NA)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("I0.0 [NA]", color = ManaCyan, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                        Text("┤ ├", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    }
                                    // Contacto I0.1 (NC)
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("I0.1 [NC]", color = HazardRed, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                        Text("┤/├", color = HazardRed, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    }
                                    // Bobina Q0.0
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Q0.0 (KM1)", color = SovereignPurple, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                        Text("─( )─", color = SovereignPurple, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    }
                                    // Raíl 0V
                                    Box(modifier = Modifier.width(3.dp).height(40.dp).background(ElectricBlueContainer))
                                }
                            }

                            Text(
                                text = "Paso 1: El operario pulsa I0.0 (Marcha). La bobina Q0.0 se energiza e inmediatamente activa la memoria interna M0.0, manteniendo la línea activa cuando se suelta el pulsador físico.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // PELDAÑO 001: TEMPORIZADOR TON
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerMid)
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "RUNG 001 // TEMPORIZADOR TON T37 (5000 MS)",
                                    color = SovereignPurple,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "TRANSICIÓN",
                                    color = ManaCyan,
                                    fontSize = 8.sp
                                )
                            }

                            // Visual Bloque TON
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceContainerLowest)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(modifier = Modifier.width(3.dp).height(40.dp).background(ManaCyan))
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("Q0.0 [NA]", color = ElectricBlue, fontSize = 8.sp)
                                        Text("┤ ├", color = TextPrimary, fontSize = 16.sp)
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(SurfaceContainerMid)
                                            .border(1.dp, ManaCyan, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 12.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text("TON: T37", color = ManaCyan, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                            Text("PT: 5000ms", color = TextPrimary, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("KM2 (Δ)", color = SovereignPurple, fontSize = 8.sp)
                                        Text("─( )─", color = SovereignPurple, fontSize = 16.sp)
                                    }
                                    Box(modifier = Modifier.width(3.dp).height(40.dp).background(ElectricBlueContainer))
                                }
                            }

                            Text(
                                text = "Paso 2: Durante los primeros 5.0s funciona en estrella (KM3 activado reduciendo el par y la corriente a 1/3). Cumplido el preset de T37, la salida conmuta a KM2 (Triángulo) para régimen nominal de par completo.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // BIBLIOTECA EXPANDIBLE DE DIAGRAMAS
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BIBLIOTECA EXPANDIBLE",
                    color = TextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "NIVEL 1 A 4",
                    color = TextSecondary,
                    fontSize = 9.sp
                )
            }

            // Card Residencial
            SchematicLibraryCard(
                category = "RESIDENCIAL // REBT",
                spec = "230V",
                title = "Cuadro General con ICG 30mA + PIAs",
                description = "Protección diferencial súperinmunizada + circuitos C1 (10A), C2 (16A) y C3 Horno (25A).",
                icon = Icons.Default.ElectricalServices,
                accentColor = ElectricBlue,
                onClick = { viewModel.selectSchematic("res_panel") }
            )

            // Card Refrigeración
            SchematicLibraryCard(
                category = "CLIMATIZACIÓN // INDUSTRIAL",
                spec = "R-404A / R-134a",
                title = "Circuito Frigorífico con Presostato Danfoss",
                description = "Lógica de Pump-Down, control termostático por solenoide y rearme automático de baja.",
                icon = Icons.Default.AcUnit,
                accentColor = ManaCyan,
                onClick = { viewModel.selectSchematic("danfoss_hvac") }
            )

            // Card PLC S7-1200
            SchematicLibraryCard(
                category = "HARDWARE PLC // DIGITAL I/O",
                spec = "24VDC",
                title = "Siemens S7-1200: E/S Sinking vs Sourcing",
                description = "Configuración PNP vs NPN en bornes de alimentación 1M / 2M con optoacopladores.",
                icon = Icons.Default.DeveloperBoard,
                accentColor = SovereignPurple,
                onClick = { viewModel.selectSchematic("plc_sinking") }
            )
        }

        // EVALUACIÓN DE COMBATE TÉCNICO Y BOTONES AUXILIARES
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(SovereignPurpleContainer, SurfaceContainerHigh)
                    )
                )
                .clickable { onNavigate(MonarchScreen.MONARCA_IA) }
                .padding(14.dp)
                .testTag("ai_examine_action_button")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SovereignPurple),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "EVALUACIÓN DE COMBATE TÉCNICO",
                            color = SovereignPurple,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Pedir al Monarca IA que me examine",
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Genera 3 preguntas de diagnóstico sobre fallas en este circuito",
                            color = TextSecondary,
                            fontSize = 10.sp
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = SovereignPurple
                )
            }
        }

        // Botones Descargar PDF y Compartir
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLow)
                    .clickable { viewModel.askMonarch("Monarca, genera la ficha técnica descargable en PDF de este esquema de arranque estrella-triángulo.") }
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = ManaCyan, modifier = Modifier.size(16.dp))
                    }
                    Column {
                        Text("PLANO A4 / A3", color = ManaCyan, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        Text("Descargar PDF", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLow)
                    .clickable { viewModel.shareHunterProfile(context) }
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.ShareLocation, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(16.dp))
                    }
                    Column {
                        Text("COLEGIAR RED", color = ElectricBlue, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        Text("Compartir PWA", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DiagnosticNodeRow(
    nodeNumber: Int,
    isSelected: Boolean,
    title: String,
    description: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) SurfaceContainerHigh else SurfaceContainerLow)
            .border(
                1.dp,
                if (isSelected) ManaCyan else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(10.dp)
            .testTag("diagnostic_node_$nodeNumber")
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$nodeNumber",
                    color = TextPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    color = TextSecondary,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SchematicLibraryCard(
    category: String,
    spec: String,
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLow)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(category, color = accentColor, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        Text("•", color = TextSecondary, fontSize = 8.sp)
                        Text(spec, color = TextSecondary, fontSize = 8.sp)
                    }
                    Text(title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(description, color = TextSecondary, fontSize = 10.sp, maxLines = 1)
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextSecondary
            )
        }
    }
}
