package com.jcross.prototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.theme.BackgroundLight
import com.jcross.prototype.ui.theme.DividerColor
import com.jcross.prototype.ui.theme.Primary
import com.jcross.prototype.ui.theme.Surface
import com.jcross.prototype.ui.theme.TextPrimary
import com.jcross.prototype.ui.theme.TextSecondary

/**
 * Режим автозаполнения X
 */
enum class AutoFillXMode(val label: String, val shortLabel: String, val explanation: String) {
    OFF(
        "Auto fill X: Off",
        "Off",
        "Does not fill X automatically."
    ),
    BASE(
        "Auto fill X: Base",
        "Base",
        "Fills X only on completed lines and columns."
    ),
    SMART(
        "Auto fill X: Smart",
        "Smart",
        "Smart mode additionally fills X around first, last and max-length blocks."
    )
}

/**
 * Экран настроек в стиле Material Design 3
 */
@Composable
fun OptionsScreen(
    onBackClick: () -> Unit
) {
    var multiClickEnabled by remember { mutableStateOf(false) }
    var autoFillNumbersEnabled by remember { mutableStateOf(true) }
    var autoFillXMode by remember { mutableStateOf(AutoFillXMode.SMART) }

    Scaffold(
        bottomBar = {
            JCrossTopBar(
                title = "Settings",
                onBackClick = onBackClick
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Gameplay Section
            SettingsSection(title = "Gameplay") {
                // MultiClick
                SettingsSwitchItem(
                    title = "MultiClick",
                    subtitle = "Sequentially change cell value by clicking on it",
                    checked = multiClickEnabled,
                    onCheckedChange = { multiClickEnabled = it }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 16.dp),
                    color = DividerColor
                )

                // Auto fill numbers
                SettingsSwitchItem(
                    title = "Auto fill numbers",
                    subtitle = "Marks numbers when you solve whole line or column",
                    checked = autoFillNumbersEnabled,
                    onCheckedChange = { autoFillNumbersEnabled = it }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 16.dp),
                    color = DividerColor
                )

                // Auto fill X
                SettingsSegmentedItem(
                    title = "Auto fill X",
                    subtitle = autoFillXMode.explanation,
                    options = AutoFillXMode.entries.map { it.shortLabel },
                    selectedIndex = autoFillXMode.ordinal,
                    onOptionSelected = { index ->
                        autoFillXMode = AutoFillXMode.entries[index]
                    }
                )
            }

            // Purchases Section
            SettingsSection(title = "Purchases") {
                // Turn off ads
                SettingsClickableItem(
                    title = "Turn off ads",
                    subtitle = "Disable ads for the entire period of using the app",
                    onClick = { /* TODO: Buy turn off ads */ }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 16.dp),
                    color = DividerColor
                )

                // Restore purchases
                SettingsClickableItem(
                    title = "Restore purchases",
                    subtitle = "Restore previously made purchases",
                    onClick = { /* TODO: Restore purchases */ }
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Primary,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Surface,
            tonalElevation = 1.dp
        ) {
            Column(content = content)
        }
    }
}

@Composable
private fun SettingsSwitchItem(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = TextSecondary,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Surface,
                checkedTrackColor = Primary,
                uncheckedThumbColor = Surface,
                uncheckedTrackColor = DividerColor,
                uncheckedBorderColor = TextSecondary
            )
        )
    }
}

@Composable
private fun SettingsSegmentedItem(
    title: String,
    subtitle: String,
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = TextSecondary,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Segmented Button Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(DividerColor),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = index == selectedIndex
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(2.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(if (isSelected) Primary else DividerColor.copy(alpha = 0f))
                        .clickable { onOptionSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) Surface else TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsClickableItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = TextSecondary,
            lineHeight = 18.sp
        )
    }
}
