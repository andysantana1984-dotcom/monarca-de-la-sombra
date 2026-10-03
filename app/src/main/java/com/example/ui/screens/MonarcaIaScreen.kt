package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schema
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.ChatMessageEntity
import com.example.ui.components.MonarchScreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.HazardRed
import com.example.ui.theme.ManaCyan
import com.example.ui.theme.ManaCyanDim
import com.example.ui.theme.OnElectricBlue
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

@Composable
fun MonarcaIaScreen(
    viewModel: MonarchViewModel,
    messages: List<ChatMessageEntity>,
    quizIndex: Int?,
    quizFeedback: String?,
    onNavigate: (MonarchScreen) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Auto scroll down when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("monarca_ia_screen")
    ) {
        // ENCABEZADO DE LA CÁMARA: AURA SOMBRÍA & MAESTRO SUPREMO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(SurfaceContainerLow)
                .border(1.dp, SovereignPurple.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(SovereignPurple),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Text(
                            text = "CÁMARA DEL MONARCA SUPREMO",
                            color = ManaCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SurfaceContainerLowest)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(ManaCyan)
                        )
                        Text(
                            text = "ENLACE SOBERANO V12",
                            color = ManaCyan,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar del Maestro Monarca de las Sombras
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerHigh)
                            .border(1.dp, SovereignPurple, RoundedCornerShape(12.dp))
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuAtG3jEGeFqQulqadbwsUjQK282oijOkxmeb6GYZlrl_sIyZAngiDwWEdWh-mT1zTejzXjk8dXqKMx_fNt6CbhngdEzkbKa_5BUA9sYAB0DZFpRU6cV4ZumPyrrZUUMiIydELg1UfuXe6a-jnEocv7I2gxmtgX7ciEpkUFM3OzEmB6Wiva1qjjqsXkFLUINOSWcsATOTf28DNSSSzUAcsxfUmebaWwjNchstVBtVl64r5D9Xc1MSAND",
                            contentDescription = "Maestro Monarca de las Sombras",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(16.dp)
                                .clip(RoundedCornerShape(topStart = 6.dp))
                                .background(ManaCyan),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Bolt,
                                contentDescription = null,
                                tint = OnManaCyan,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "MAESTRO MONARCA",
                                color = TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(SovereignPurpleContainer)
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "RANGO S",
                                    color = SovereignPurple,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "Voz del Sistema // Tutor Personal de Ingeniería y Control",
                            color = ManaCyan,
                            fontSize = 10.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Icon(
                                Icons.Default.Analytics,
                                contentDescription = null,
                                tint = ManaCyan,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Analizando debilidades del Cazador... Conectado",
                                color = TextSecondary,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }

        // EVALUACIÓN DE COMBATE TÉCNICO INTERACTIVA (Si está activa)
        if (quizIndex != null) {
            QuizCard(
                quizIndex = quizIndex,
                feedback = quizFeedback,
                onAnswer = { viewModel.answerQuiz(it) },
                onNext = { viewModel.nextQuizQuestion() }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // DIÁLOGO PEDAGÓGICO (CHAT LIST)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                if (msg.sender == "MONARCH") {
                    MonarchMessageBubble(
                        message = msg,
                        onOpenSchematic = { onNavigate(MonarchScreen.GRIMORIO) }
                    )
                } else {
                    UserMessageBubble(message = msg)
                }
            }

            // Visualizador auxiliar de bobinados
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerLowest)
                        .border(1.dp, ManaCyan.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ManaCyanDim),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Schema,
                                    contentDescription = null,
                                    tint = TextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Visualizador Holográfico de Bobinados",
                                    color = TextPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "U1-V1-W1 // U2-V2-W2 configurables en Grimorio",
                                    color = TextSecondary,
                                    fontSize = 9.sp
                                )
                            }
                        }

                        Button(
                            onClick = { onNavigate(MonarchScreen.GRIMORIO) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceContainerHigh,
                                contentColor = ManaCyan
                            ),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(12.dp))
                                Text("ABRIR", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // COMANDOS RÁPIDOS DEL SISTEMA (CHIPS)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "COMANDOS DEL SISTEMA",
                    color = ManaCyan,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Toca para invocar",
                    color = TextSecondary,
                    fontSize = 9.sp
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf(
                    "¿Por dónde empiezo a estudiar refrigeración?",
                    "Temporizador Y-Δ paso a paso",
                    "¿Cómo conecto sensor PNP a PLC?"
                ).forEach { command ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerHigh)
                            .clickable {
                                inputText = command
                                viewModel.askMonarch(command)
                                inputText = ""
                            }
                            .padding(horizontal = 6.dp, vertical = 6.dp)
                            .testTag("quick_command_chip"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = command,
                            color = TextPrimary,
                            fontSize = 9.sp,
                            maxLines = 2,
                            lineHeight = 11.sp
                        )
                    }
                }
            }
        }

        // BARRA DE ENTRADA DE MENSAJES (INPUT HUD)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHigh)
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Adjuntar Diagrama
                IconButton(
                    onClick = {
                        viewModel.askMonarch("Monarca, he adjuntado una fotografía de un diagrama unifilar con arranque estrella-triángulo. ¿Cómo identifico los contactos auxiliares 13-14?")
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerMid)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Adjuntar Diagrama",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Campo de Texto
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = "Invocar sabiduría del Monarca...",
                            color = MaterialTheme.colorScheme.outlineVariant,
                            fontSize = 12.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("monarch_input_field"),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = SurfaceContainerLowest,
                        unfocusedContainerColor = SurfaceContainerLowest,
                        focusedBorderColor = ManaCyan,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                // Botón Examen Técnico
                IconButton(
                    onClick = { viewModel.startQuiz() },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerMid)
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = "Pedir Evaluación Técnica",
                        tint = SovereignPurple,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Botón Enviar con Rayo
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            viewModel.askMonarch(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ManaCyanDim)
                        .testTag("send_question_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = "Enviar",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MonarchMessageBubble(
    message: ChatMessageEntity,
    onOpenSchematic: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Icon(
                Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = SovereignPurple,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = "DICTAMEN DEL SOBERANO",
                color = SovereignPurple,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            if (message.stepPhase != null) {
                Text(
                    text = "• ${message.stepPhase}",
                    color = TextSecondary,
                    fontSize = 9.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerHigh)
                .border(1.dp, ManaCyan.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.ElectricBolt,
                        contentDescription = null,
                        tint = ManaCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = message.content,
                        color = TextPrimary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }

                // Botones interactivos de acción
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Button(
                        onClick = onOpenSchematic,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlueContainer,
                            contentColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Schema, contentDescription = null, modifier = Modifier.size(14.dp))
                            Text("VER ESQUEMA EN GRIMORIO", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SurfaceContainerLowest,
                            contentColor = TextSecondary
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(14.dp))
                            Text("CALCULAR AWG", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ManaCyan, modifier = Modifier.size(12.dp))
                        Text("Verificado con IEC 60947", color = TextSecondary, fontSize = 9.sp)
                    }
                    Text("Directiva Activa", color = TextSecondary, fontSize = 9.sp)
                }
            }
        }
    }
}

@Composable
private fun UserMessageBubble(message: ChatMessageEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "CAZADOR APRENDIZ",
                color = ElectricBlue,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Icon(
                Icons.Default.Bolt,
                contentDescription = null,
                tint = ElectricBlue,
                modifier = Modifier.size(12.dp)
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceContainerLowest)
                .border(1.dp, ElectricBlue.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(
                text = message.content,
                color = TextPrimary,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun QuizCard(
    quizIndex: Int,
    feedback: String?,
    onAnswer: (Int) -> Unit,
    onNext: () -> Unit
) {
    val questions = listOf(
        Triple(
            "¿Qué ocurre si el contactor KM3 (Estrella) no abre antes de que cierre KM2 (Triángulo)?",
            listOf(
                "A) El motor gira en reversa silenciosamente.",
                "B) Cortocircuito violento trifásico entre las fases R-S-T.",
                "C) El temporizador aumenta su tiempo 5 segundos."
            ),
            1
        ),
        Triple(
            "En un circuito frigorífico con presostato Danfoss KP15, ¿cuál es la función del presostato de baja?",
            listOf(
                "A) Apagar el ventilador del condensador.",
                "B) Detectar exceso de temperatura en el cárter.",
                "C) Proteger el compresor de cavitación / vacío extremo o fuga de refrigerante."
            ),
            2
        ),
        Triple(
            "En un PLC Siemens S7-1200 configurado como Sinking, ¿qué tipo de sensor exterior debes conectar?",
            listOf(
                "A) Sensor PNP que suministra señal positiva (+24VDC).",
                "B) Termocupla sin amplificador operacional.",
                "C) Sensor NPN que conmuta directamente a tierra (0V)."
            ),
            0
        )
    )

    val currentQ = questions[quizIndex]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerMid)
            .border(1.dp, SovereignPurple, RoundedCornerShape(12.dp))
            .padding(14.dp)
            .testTag("quiz_card")
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EVALUACIÓN DE COMBATE TÉCNICO (${quizIndex + 1}/3)",
                    color = SovereignPurple,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "+150 XP",
                    color = ManaCyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = currentQ.first,
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                currentQ.second.forEachIndexed { index, option ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest)
                            .clickable { onAnswer(index) }
                            .padding(8.dp)
                            .testTag("quiz_option_$index")
                    ) {
                        Text(
                            text = option,
                            color = TextPrimary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            if (feedback != null) {
                Text(
                    text = feedback,
                    color = if (feedback.startsWith("¡EXACTO")) ManaCyan else HazardRed,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onNext,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SovereignPurpleContainer,
                        contentColor = TextPrimary
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(if (quizIndex < 2) "SIGUIENTE PREGUNTA" else "FINALIZAR EVALUACIÓN", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
