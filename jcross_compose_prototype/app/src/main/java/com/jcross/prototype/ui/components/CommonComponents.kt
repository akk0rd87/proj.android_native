package com.jcross.prototype.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcross.prototype.ui.theme.*

/**
 * Карточка категории размера на главном экране
 */
@Composable
fun SizeCard(
    shortLabel: String,
    fullLabel: String,
    puzzleCount: Int,
    progressPercent: Float,
    color: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCompact: Boolean = false
) {
    // Adaptive sizes based on card compactness
    val iconSize = if (isCompact) 50.dp else 70.dp
    val labelFontSize = if (isCompact) 20.sp else 28.sp
    val titleFontSize = if (isCompact) 18.sp else 24.sp
    val puzzlesFontSize = if (isCompact) 11.sp else 14.sp
    val percentFontSize = if (isCompact) 16.sp else 20.sp
    val cardPadding = if (isCompact) 12.dp else 16.dp
    val spacerWidth = if (isCompact) 12.dp else 24.dp
    val progressBarHeight = if (isCompact) 6.dp else 8.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = if (isCompact) 72.dp else 96.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = ShadowColor
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(cardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = shortLabel,
                    fontSize = labelFontSize,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            Spacer(modifier = Modifier.width(spacerWidth))

            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = fullLabel,
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                if (!isCompact) {
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = "$puzzleCount puzzles",
                    fontSize = puzzlesFontSize,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(if (isCompact) 4.dp else 8.dp))

                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(progressBarHeight)
                        .clip(RoundedCornerShape(4.dp))
                        .background(DividerColor)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressPercent)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(4.dp))
                            .background(color)
                    )
                }
            }

            Spacer(modifier = Modifier.width(if (isCompact) 8.dp else 16.dp))

            // Progress percentage
            Text(
                text = "${(progressPercent * 100).toInt()}%",
                fontSize = percentFontSize,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
            )
        }
    }
}

/**
 * Элемент списка папок
 */
@Composable
fun FolderItem(
    name: String,
    totalPuzzles: Int,
    solvedPuzzles: Int,
    progressPercent: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = ShadowColor
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )

                Text(
                    text = "$solvedPuzzles / $totalPuzzles",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(DividerColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressPercent)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(3.dp))
                        .background(Primary)
                )
            }
        }
    }
}

/**
 * Элемент списка головоломок
 */
@Composable
fun PuzzleItem(
    name: String,
    size: String,
    isSolved: Boolean,
    timeSpent: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = ShadowColor
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSolved) ColorSLight else Surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = size,
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }

            if (isSolved && timeSpent != null) {
                Text(
                    text = formatTime(timeSpent),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = ColorS
                )
            }
        }
    }
}

/**
 * Нижняя панель навигации
 */
@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == selectedIndex) Primary
                            else DividerColor
                        )
                        .clickable { onItemSelected(index) }
                )
            }
        }
    }
}

/**
 * Форматирование времени в мм:сс
 */
private fun formatTime(seconds: Int): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%d:%02d", mins, secs)
}

/**
 * Top App Bar с заголовком и кнопкой назад
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JCrossTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Text("←", fontSize = 28.sp, color = TextPrimary)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Surface
        ),
        modifier = modifier
    )
}
