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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
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
 * Карточка папки для grid-макета
 */
@Composable
fun FolderGridCard(
    width: Int,
    height: Int,
    totalPuzzles: Int,
    solvedPuzzles: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
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
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Размер кроссворда
            Text(
                text = "${width}x${height}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Количество решенных/всего
            Text(
                text = "$solvedPuzzles/$totalPuzzles",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Прогресс-бар
            val progressPercent = if (totalPuzzles > 0) solvedPuzzles.toFloat() / totalPuzzles else 0f
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(DividerColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressPercent)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(2.dp))
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

            if (isSolved) {
                Text(
                    text = "✓",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorS
                )
            }
        }
    }
}

/**
 * Карточка головоломки для grid-макета
 */
@Composable
fun PuzzleGridCard(
    puzzleNumber: Int,
    isSolved: Boolean,
    isInProgress: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = ShadowColor
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSolved -> ColorSLight
                isInProgress -> ColorLLight
                else -> Surface
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Номер головоломки
            Text(
                text = "$puzzleNumber",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Иконка статуса
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isSolved -> ColorS
                            isInProgress -> ColorL
                            else -> DividerColor
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        isSolved -> "✓"
                        isInProgress -> "•"
                        else -> ""
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Данные для иконок нижней панели навигации
 */
data class NavBarIcon(
    val svgFileName: String,
    val contentDescription: String
)

/**
 * Нижняя панель навигации с SVG иконками (оригинальное качество)
 */
@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val icons = listOf(
        NavBarIcon("monkey.svg", "Home"),
        NavBarIcon("settings.svg", "Settings"),
        NavBarIcon("brush.svg", "Themes"),
        NavBarIcon("info.svg", "Info"),
        NavBarIcon("rate.svg", "Rate"),
        NavBarIcon("envelope.svg", "Contact")
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            icons.forEachIndexed { index, navIcon ->
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == selectedIndex) Primary.copy(alpha = 0.15f)
                            else Color.Transparent
                        )
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/svg/${navIcon.svgFileName}")
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = navIcon.contentDescription,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
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
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        color = DividerColor,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Кнопка назад
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(64.dp)
                ) {
                    Text("←", fontSize = 28.sp, color = TextPrimary)
                }
            } else {
                Spacer(modifier = Modifier.width(64.dp))
            }

            // Заголовок по центру
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            // Пустое пространство справа для баланса
            Spacer(modifier = Modifier.width(64.dp))
        }
    }
}
