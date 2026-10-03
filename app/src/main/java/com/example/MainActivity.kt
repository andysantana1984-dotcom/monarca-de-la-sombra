package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.ui.components.MonarchBottomNav
import com.example.ui.components.MonarchScreen
import com.example.ui.components.MonarchTopBar
import com.example.ui.screens.EstadoScreen
import com.example.ui.screens.GrimorioScreen
import com.example.ui.screens.MazmorrasScreen
import com.example.ui.screens.MonarcaIaScreen
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.ManaCyan
import com.example.ui.theme.ManaCyanDim
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SovereignPurple
import com.example.ui.theme.SovereignPurpleContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.SurfaceContainerMid
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.MonarchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MonarchApp()
            }
        }
    }
}

@Composable
fun MonarchApp(viewModel: MonarchViewModel = viewModel()) {
    val context = LocalContext.current
    var currentScreen by rememberSaveable { mutableStateOf(MonarchScreen.ESTADO) }
    var showAvatarDialog by remember { mutableStateOf(false) }
    var showInstallDialog by remember { mutableStateOf(false) }

    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val dungeons by viewModel.dungeons.collectAsStateWithLifecycle()
    val chatMessages by viewModel.chatMessages.collectAsStateWithLifecycle()
    val timerSeconds by viewModel.timerSecondsLeft.collectAsStateWithLifecycle()
    val isTimerRunning by viewModel.isTimerRunning.collectAsStateWithLifecycle()
    val systemToast by viewModel.systemToast.collectAsStateWithLifecycle()
    val isCurrentFlowing by viewModel.isCurrentFlowing.collectAsStateWithLifecycle()
    val selectedNode by viewModel.selectedSchematicNode.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedSchematicCategory.collectAsStateWithLifecycle()
    val quizIndex by viewModel.currentQuizIndex.collectAsStateWithLifecycle()
    val quizFeedback by viewModel.quizFeedback.collectAsStateWithLifecycle()

    // Handle back button press
    if (currentScreen != MonarchScreen.ESTADO) {
        BackHandler {
            currentScreen = MonarchScreen.ESTADO
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("monarch_main_scaffold"),
        topBar = {
            MonarchTopBar(
                currentTitle = currentScreen.title,
                userProfile = userProfile,
                onShareClick = { viewModel.shareHunterProfile(context) },
                onInstallClick = { showInstallDialog = true },
                onAvatarClick = { showAvatarDialog = true }
            )
        },
        bottomBar = {
            MonarchBottomNav(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreen = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentScreen) {
                MonarchScreen.ESTADO -> EstadoScreen(
                    viewModel = viewModel,
                    userProfile = userProfile,
                    timerSeconds = timerSeconds,
                    isTimerRunning = isTimerRunning,
                    onNavigate = { currentScreen = it },
                    onShowInstallDialog = { showInstallDialog = true }
                )
                MonarchScreen.MAZMORRAS -> MazmorrasScreen(
                    viewModel = viewModel,
                    userProfile = userProfile,
                    dungeons = dungeons,
                    onNavigate = { currentScreen = it }
                )
                MonarchScreen.MONARCA_IA -> MonarcaIaScreen(
                    viewModel = viewModel,
                    messages = chatMessages,
                    quizIndex = quizIndex,
                    quizFeedback = quizFeedback,
                    onNavigate = { currentScreen = it }
                )
                MonarchScreen.GRIMORIO -> GrimorioScreen(
                    viewModel = viewModel,
                    isCurrentFlowing = isCurrentFlowing,
                    selectedNode = selectedNode,
                    selectedCategory = selectedCategory,
                    onNavigate = { currentScreen = it }
                )
            }

            // HUD System Toast / Alerta Flotante
            AnimatedVisibility(
                visible = systemToast != null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                systemToast?.let { toastMsg ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLowest.copy(alpha = 0.95f))
                            .border(1.dp, ManaCyan, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(ManaCyanDim),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = TextPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "NOTIFICACIÓN DEL SISTEMA",
                                    color = ManaCyan,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = toastMsg,
                                    color = TextPrimary,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal de Selección de Avatar de Cazador / Monarca
    if (showAvatarDialog) {
        AlertDialog(
            onDismissRequest = { showAvatarDialog = false },
            confirmButton = {
                Button(
                    onClick = { showAvatarDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = ManaCyanDim)
                ) {
                    Text("CERRAR", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = ManaCyan)
                    Text("Sincronizar Avatar del Cazador", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        "Selecciona la imagen de manifestación de tu núcleo en el Sistema:",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    val avatars = listOf(
                        Triple(
                            "jinwoo",
                            "Cazador Jin-Woo (Aprendiz Eléctrico)",
                            "https://lh3.googleusercontent.com/aida-public/AB6AXuBTX4QtwR1QWAeGzF0Qkf1hpDGuDAolMgT228nePTEUZJjrHJojc-bae_9OgtlDIKAXwC9o41TJYWa4MH3IrL73M2A_H7FCM0vVsDl4vNq06PbXtEi_6QI_tzcFQwqWMxSp5DHixBacD3T5fnnFy2RK5BMoqMCsLA1w7PdB1CUUp2aJem4Wgm3uZfFfSfBzzs8N2z4gMfmFPPtwEB7e9-g1m3huqy4Uq-58v7L2fu4j73rcOjaZxc3r"
                        ),
                        Triple(
                            "monarch",
                            "Maestro Monarca de las Sombras",
                            "https://lh3.googleusercontent.com/aida-public/AB6AXuAtG3jEGeFqQulqadbwsUjQK282oijOkxmeb6GYZlrl_sIyZAngiDwWEdWh-mT1zTejzXjk8dXqKMx_fNt6CbhngdEzkbKa_5BUA9sYAB0DZFpRU6cV4ZumPyrrZUUMiIydELg1UfuXe6a-jnEocv7I2gxmtgX7ciEpkUFM3OzEmB6Wiva1qjjqsXkFLUINOSWcsATOTf28DNSSSzUAcsxfUmebaWwjNchstVBtVl64r5D9Xc1MSAND"
                        ),
                        Triple(
                            "operative",
                            "Operativo Técnico Nivel 12",
                            "https://lh3.googleusercontent.com/aida-public/AB6AXuB2Md2mY36z2KPn5VJQvl0X7oadcGG8FORShAnRyVZsclRrPYzL6EUWiJbr3yMOWAtYknOouww0CQGPVRNNoGYRrbypw086e1mA_COkhaOKNeZoEnJNfjffFIWN0TRPihqoOolyTxoG9rsRnwSWOA95wVMmYekHGHe7Y97mutyAB5aHN_uuJNg5_mJ8suzgF9d7QN6ZzrQ2tYl9_ZlC2rWsAk4l7rqHh3NhVZxkEOhtyBS2h3peVERt"
                        )
                    )

                    avatars.forEach { (id, label, url) ->
                        val isSelected = userProfile.avatarCustomUrl == url
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) SurfaceContainerHigh else SurfaceContainerLowest)
                                .border(1.dp, if (isSelected) ManaCyan else MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.setAvatar(id)
                                    showAvatarDialog = false
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerMid)
                            ) {
                                AsyncImage(
                                    model = url,
                                    contentDescription = label,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Column {
                                Text(label, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("Vincular aspecto al Sistema", color = TextSecondary, fontSize = 9.sp)
                            }
                        }
                    }
                }
            },
            containerColor = SurfaceContainerMid,
            shape = RoundedCornerShape(14.dp)
        )
    }

    // Modal de Instalación del Sistema y Persistencia SQLite
    if (showInstallDialog) {
        AlertDialog(
            onDismissRequest = { showInstallDialog = false },
            confirmButton = {
                Button(
                    onClick = { showInstallDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueContainer)
                ) {
                    Text("ENTENDIDO", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.InstallMobile, contentDescription = null, tint = ManaCyan)
                    Text("Instalación & Persistencia SQLite", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Storage, contentDescription = null, tint = SovereignPurple)
                        Text(
                            "Base de Datos SQLite (Room) Activa",
                            color = SovereignPurple,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        "1. Tu perfil, nivel (N14), XP acumulada, racha y horas diarias se guardan de forma 100% segura en la base de datos SQLite interna de tu dispositivo.\n\n" +
                                "2. Los diagramas técnicos de electricidad industrial, refrigeración y PLC están cacheados localmente para que puedas consultarlos en el taller o fábrica sin conexión a internet.\n\n" +
                                "3. Puedes exportar esta app como APK nativo desde AI Studio o compartir el enlace de invocación con tus colegas técnicos.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            },
            containerColor = SurfaceContainerMid,
            shape = RoundedCornerShape(14.dp)
        )
    }
}
