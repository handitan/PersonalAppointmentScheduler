package com.handitan.personalappointmentscheduler.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = RedOrange80,
    primaryContainer = RedOrange75,
    surfaceVariant = Pink30,
)

private val LightColorScheme = lightColorScheme(
    primary = RedOrange80,
    primaryContainer = RedOrange75,
    surfaceVariant = Pink30,
//    onPrimary = Color.White,
//    onSurfaceVariant = Color.White,
//    background = Color.Black,
//    onBackground = Color.White,
//    primary = Green40,
//    onPrimary = Color.White,
//    primaryContainer = Green90,
//    onPrimaryContainer = Green10,
//    inversePrimary = Green80,
//    secondary = DarkGreen40,
//    onSecondary = Color.White,
//    secondaryContainer = DarkGreen90,
//    onSecondaryContainer = DarkGreen10,
//    tertiary = Violet40,
//    onTertiary = Color.White,
//    tertiaryContainer = Violet90,
//    onTertiaryContainer = Violet10,
//    error = Red40,
//    onError = Color.White,
//    errorContainer = Red90,
//    onErrorContainer = Red10,
//    background = Grey99,
//    onBackground = Grey10,
//    surface = GreenGrey90,
//    onSurface = GreenGrey30,
//    inverseSurface = Grey20,
//    inverseOnSurface = Grey95,
//    surfaceVariant = GreenGrey90,
//    onSurfaceVariant = GreenGrey30,
//    outline = GreenGrey50
)

@Composable
fun PersonalAppointmentSchedulerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
//        when {
////        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
////            val context = LocalContext.current
////            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
////        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}