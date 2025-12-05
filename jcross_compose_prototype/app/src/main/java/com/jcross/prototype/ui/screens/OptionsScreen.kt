package com.jcross.prototype.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.theme.TextPrimary

/**
 * Экран настроек
 */
@Composable
fun OptionsScreen(
    onBackClick: () -> Unit
) {
    var settings by remember { mutableStateOf(MockDataProvider.getSettings()) }

    Scaffold(
        bottomBar = {
            JCrossTopBar(
                title = "Settings",
                onBackClick = onBackClick
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Audio",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            SettingsRow(
                label = "Sound Effects",
                checked = settings.soundEnabled,
                onCheckedChange = { settings = settings.copy(soundEnabled = it) }
            )

            SettingsRow(
                label = "Music",
                checked = settings.musicEnabled,
                onCheckedChange = { settings = settings.copy(musicEnabled = it) }
            )

            SettingsRow(
                label = "Vibration",
                checked = settings.vibrateEnabled,
                onCheckedChange = { settings = settings.copy(vibrateEnabled = it) }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Gameplay",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            SettingsRow(
                label = "Show Timer",
                checked = settings.showTimer,
                onCheckedChange = { settings = settings.copy(showTimer = it) }
            )

            SettingsRow(
                label = "Highlight Errors",
                checked = settings.showErrors,
                onCheckedChange = { settings = settings.copy(showErrors = it) }
            )

            SettingsRow(
                label = "Auto-fill Completed Lines",
                checked = settings.autoFillEnabled,
                onCheckedChange = { settings = settings.copy(autoFillEnabled = it) }
            )
        }
    }
}

@Composable
fun SettingsRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = TextPrimary
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
