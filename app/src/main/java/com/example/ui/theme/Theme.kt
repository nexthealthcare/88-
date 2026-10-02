package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = WellnessBluePrimary,
    onPrimary = WellnessSurface,
    primaryContainer = WellnessBlueDark,
    onPrimaryContainer = WellnessBlueContainer,
    secondary = WellnessTeal,
    onSecondary = WellnessSurface,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    surfaceVariant = Color(0xFF334155),
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = WellnessBluePrimary,
    onPrimary = Color.White,
    primaryContainer = WellnessBlueContainer,
    onPrimaryContainer = WellnessBlueOnContainer,
    secondary = WellnessTeal,
    onSecondary = Color.White,
    secondaryContainer = WellnessTealContainer,
    onSecondaryContainer = Color(0xFF004D40),
    tertiary = WellnessOrangeAccent,
    background = WellnessBackground,
    surface = WellnessSurface,
    surfaceVariant = WellnessSurfaceVariant,
    onBackground = WellnessTextPrimary,
    onSurface = WellnessTextPrimary,
    outline = WellnessOutline
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
