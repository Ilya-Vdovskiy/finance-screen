package com.example.ui_with_jetpack_compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = Color(0xFFFFDD2D),
    onPrimary = Color(0xFF171717),
    primaryContainer = Color(0xFFFFE77B),
    onPrimaryContainer = Color(0xFF171717),
    secondary = Color(0xFF35486A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8EDF6),
    onSecondaryContainer = Color(0xFF27364D),
    tertiary = Color(0xFF246B54),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDDF3E8),
    onTertiaryContainer = Color(0xFF17533F),
    error = Color(0xFFAB3341),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFBE8E9),
    onErrorContainer = Color(0xFF822732),
    background = Color(0xFFF5F6F7),
    onBackground = Color(0xFF17191D),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF17191D),
    surfaceVariant = Color(0xFFEDF0F2),
    onSurfaceVariant = Color(0xFF4C5561),
    outline = Color(0xFF727B85),
    outlineVariant = Color(0xFFDDE1E5),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFDD2D),
    onPrimary = Color(0xFF171717),
    primaryContainer = Color(0xFF6A5911),
    onPrimaryContainer = Color(0xFFFFE991),
    secondary = Color(0xFFAEC5E8),
    onSecondary = Color(0xFF192944),
    secondaryContainer = Color(0xFF26334A),
    onSecondaryContainer = Color(0xFFD7E5FA),
    tertiary = Color(0xFF9ED8BC),
    onTertiary = Color(0xFF123E2D),
    tertiaryContainer = Color(0xFF1D4435),
    onTertiaryContainer = Color(0xFFBCE8D0),
    error = Color(0xFFFFADB4),
    onError = Color(0xFF5E1721),
    errorContainer = Color(0xFF51252B),
    onErrorContainer = Color(0xFFFFD9DC),
    background = Color(0xFF111318),
    onBackground = Color(0xFFF1F2F4),
    surface = Color(0xFF1D2026),
    onSurface = Color(0xFFF1F2F4),
    surfaceVariant = Color(0xFF292D35),
    onSurfaceVariant = Color(0xFFC4CBD4),
    outline = Color(0xFFA8B1BE),
    outlineVariant = Color(0xFF3C424C),
)

private val FinanceTypography = Typography(
    displaySmall = TextStyle(fontSize = 36.sp, lineHeight = 42.sp, fontWeight = FontWeight.Bold),
    headlineMedium = TextStyle(fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold),
    headlineSmall = TextStyle(fontSize = 22.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold),
    titleLarge = TextStyle(fontSize = 19.sp, lineHeight = 25.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = TextStyle(fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 16.sp, lineHeight = 23.sp),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.SemiBold),
    labelMedium = TextStyle(fontSize = 12.sp, lineHeight = 17.sp, fontWeight = FontWeight.Medium),
)

private val FinanceShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(28.dp),
)

@Composable
fun FinanceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = FinanceTypography,
        shapes = FinanceShapes,
        content = content,
    )
}
