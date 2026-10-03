package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ManhwaColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = OnElectricBlue,
    primaryContainer = ElectricBlueContainer,
    onPrimaryContainer = TextPrimary,
    secondary = SovereignPurple,
    onSecondary = OnSovereignPurple,
    secondaryContainer = SovereignPurpleContainer,
    onSecondaryContainer = SovereignPurple,
    tertiary = ManaCyan,
    onTertiary = OnManaCyan,
    tertiaryContainer = ManaCyanDim,
    onTertiaryContainer = TextPrimary,
    background = SurfaceObsidian,
    onBackground = TextPrimary,
    surface = SurfaceObsidian,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = TextSecondary,
    outline = OutlineGrey,
    outlineVariant = OutlineVariantGrey,
    error = HazardRed,
    onError = OnHazardRed,
    errorContainer = HazardRedContainer,
    onErrorContainer = TextPrimary,
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainerMid,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ManhwaColorScheme,
        typography = Typography,
        content = content
    )
}
