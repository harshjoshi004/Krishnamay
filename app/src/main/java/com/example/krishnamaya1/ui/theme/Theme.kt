package com.example.krishnamaya1.ui.theme

import android.app.Activity
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = ElevatedMustard1,
    secondary = ElevatedMustard1,
    tertiary = ElevatedMustard1,
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = ElevatedMustard1,
    onSecondary = ElevatedMustard1,
    onTertiary = ElevatedMustard1,
    onBackground = Color(0xFFFFFBFE),
    onSurface = Color(0xFFFFFBFE),
)

@Composable
fun Krishnamaya1Theme(
    content: @Composable () -> Unit
) {
    // Force the light color scheme
    val colorScheme = LightColorScheme

    // Set the system UI controller to use black navigation bar color
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(Color.Black)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}