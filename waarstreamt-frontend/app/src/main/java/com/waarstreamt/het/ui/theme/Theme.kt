package com.waarstreamt.het.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.waarstreamt.het.R

// ── Brand colours ────────────────────────────────────────────────────────────
val Teal = Color(0xFF00E6AA)
val TealDim = Color(0xFF009E75)
val Background = Color(0xFF080C10)
val Surface = Color(0xFF111820)
val SurfaceVariant = Color(0xFF1A2330)
val OnSurface = Color(0xFFE0E4EC)
val Muted = Color(0xFF6B7A8D)
val Divider = Color(0xFF1E2832)

// ── Colour scheme ─────────────────────────────────────────────────────────────
val WaarStreamtColorScheme = darkColorScheme(
    primary = Teal,
    onPrimary = Background,
    primaryContainer = TealDim,
    secondary = Color(0xFF8899AA),
    background = Background,
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    onBackground = OnSurface,
    onSurface = OnSurface,
    onSurfaceVariant = Muted,
    outline = Divider,
)

// ── Typography ────────────────────────────────────────────────────────────────
val BebasNeue = FontFamily(Font(R.font.bebas_neue_regular, FontWeight.Normal))
val DmSans = FontFamily(
    Font(R.font.dm_sans_light, FontWeight.Light),
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_semibold, FontWeight.SemiBold),
)

val WaarStreamtTypography = androidx.compose.material3.Typography(
    displayLarge = TextStyle(fontFamily = BebasNeue, fontSize = 72.sp, letterSpacing = 1.sp),
    displayMedium = TextStyle(fontFamily = BebasNeue, fontSize = 52.sp, letterSpacing = 1.sp),
    displaySmall = TextStyle(fontFamily = BebasNeue, fontSize = 36.sp, letterSpacing = 0.5.sp),
    headlineLarge = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.SemiBold, fontSize = 22.sp),
    headlineMedium = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.Medium, fontSize = 18.sp),
    titleMedium = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.Medium, fontSize = 15.sp),
    bodyLarge = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 26.sp),
    bodyMedium = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 22.sp),
    bodySmall = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.Light, fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = DmSans, fontWeight = FontWeight.Medium, fontSize = 10.sp, letterSpacing = 2.sp),
)

@Composable
fun WaarStreamtTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = WaarStreamtColorScheme,
        typography = WaarStreamtTypography,
        content = content,
    )
}
