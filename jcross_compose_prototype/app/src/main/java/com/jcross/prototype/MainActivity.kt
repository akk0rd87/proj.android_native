package com.jcross.prototype

import android.os.Bundle
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
import androidx.navigation.compose.rememberNavController
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.navigation.AppNavigation
import com.jcross.prototype.ui.theme.JCrossTheme

/**
 * Главная активность приложения
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        }
                    )
                }
            }
        }
    }
}
