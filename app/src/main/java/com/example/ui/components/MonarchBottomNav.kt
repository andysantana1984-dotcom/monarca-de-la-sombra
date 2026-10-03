package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ManaCyan
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextSecondary

enum class MonarchScreen(val title: String, val icon: ImageVector, val tag: String) {
    ESTADO("Estado", Icons.Default.Dashboard, "nav_estado"),
    MAZMORRAS("Mazmorras", Icons.Default.MilitaryTech, "nav_mazmorras"),
    MONARCA_IA("Monarca IA", Icons.Default.Psychology, "nav_monarca_ia"),
    GRIMORIO("Grimorio", Icons.Default.MenuBook, "nav_grimorio")
}

@Composable
fun MonarchBottomNav(
    currentScreen: MonarchScreen,
    onScreenSelected: (MonarchScreen) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(SurfaceContainerLowest.copy(alpha = 0.95f))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonarchScreen.values().forEach { screen ->
            val isSelected = currentScreen == screen
            val color = if (isSelected) ManaCyan else TextSecondary

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onScreenSelected(screen) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .testTag(screen.tag),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title,
                    tint = color,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = screen.title.uppercase(),
                    color = color,
                    fontSize = 9.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
