package com.jcross.prototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.model.AppTheme
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.theme.*

/**
 * Экран выбора тем
 */
@Composable
fun ThemesScreen(
    onBackClick: () -> Unit,
    onThemeSelected: (AppTheme) -> Unit = {}
) {
    var themes by remember { mutableStateOf(MockDataProvider.getThemes()) }

    Scaffold(
        topBar = {
            JCrossTopBar(
                title = "Color Themes",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(themes) { theme ->
                ThemeCard(
                    theme = theme,
                    onSelect = {
                        themes = themes.map {
                            it.copy(isSelected = it.id == theme.id)
                        }
                        onThemeSelected(theme)
                    }
                )
            }
        }
    }
}

@Composable
fun ThemeCard(
    theme: AppTheme,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (theme.isSelected) Color(theme.primaryColor).copy(alpha = 0.1f)
            else Surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Color preview circles
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(theme.primaryColor))
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(theme.secondaryColor))
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(theme.backgroundColor))
                    )
                }

                Text(
                    text = theme.name,
                    fontSize = 18.sp,
                    fontWeight = if (theme.isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = TextPrimary
                )
            }

            if (theme.isSelected) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(theme.primaryColor)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
