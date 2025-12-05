package com.jcross.prototype

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.navigation.AppNavigation
import com.jcross.prototype.ui.theme.JCrossTheme

/**
 * Главная активность приложения
 */
class MainActivity : ComponentActivity() {
    private var isImmersiveModeEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge and handle system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            var selectedTheme by remember {
                mutableStateOf(MockDataProvider.getThemes().find { it.isSelected })
            }

            JCrossTheme(appTheme = selectedTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        onThemeSelected = { theme ->
                            selectedTheme = theme
                        },
                        onImmersiveModeChange = { enabled ->
                            setImmersiveMode(enabled)
                        }
                    )
                }
            }
        }
    }

    /**
     * Включить/выключить sticky immersive режим
     */
    fun setImmersiveMode(enabled: Boolean) {
        isImmersiveModeEnabled = enabled

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ (API 30+)
            val controller = window.insetsController
            if (enabled) {
                controller?.hide(
                    android.view.WindowInsets.Type.systemBars()
                )
                controller?.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            } else {
                controller?.show(android.view.WindowInsets.Type.systemBars())
            }
        } else {
            // Android 10 and below
            @Suppress("DEPRECATION")
            if (enabled) {
                window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
            } else {
                window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
            }
        }
    }
}
