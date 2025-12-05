package com.jcross.prototype.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import kotlin.math.ceil
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.model.Group
import com.jcross.prototype.model.PuzzleSize
import com.jcross.prototype.ui.components.BottomNavigationBar
import com.jcross.prototype.ui.components.SizeCard
import com.jcross.prototype.ui.theme.*

/**
 * Главный экран с выбором размера головоломок
 */
@Composable
fun MainScreen(
    onGroupSelected: (Int) -> Unit,
    onNavigateToOptions: () -> Unit,
    onNavigateToThemes: () -> Unit
) {
    val groups = remember { MockDataProvider.getGroups() }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = 0,
                onItemSelected = { index ->
                    when (index) {
                        1 -> onNavigateToOptions()
                        2 -> onNavigateToThemes()
                        // Другие иконки можно добавить позже
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "JCROSS NONOGRAMS",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            // Content area: choose single-column (portrait) or two-column (landscape).
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val isLandscape = maxWidth > maxHeight

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BoxWithConstraints(modifier = Modifier.weight(1f)) {
                        val gap = 12.dp

                        if (!isLandscape) {
                            // Portrait: stack cards vertically and share height equally
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(gap)
                            ) {
                                groups.forEach { group ->
                                    val (color, bgColor) = getSizeColors(group.size)

                                    SizeCard(
                                        shortLabel = group.size.shortLabel,
                                        fullLabel = group.size.label,
                                        puzzleCount = group.totalPuzzles,
                                        progressPercent = group.progressPercent,
                                        color = color,
                                        backgroundColor = bgColor,
                                        onClick = { onGroupSelected(group.id) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                    )
                                }
                            }
                        } else {
                            // Landscape: render grid with 2 columns; compute cell height so grid fits without scroll
                            Log.i("MainScreen", "Landscape layout: maxW=$maxWidth maxH=$maxHeight")
                            val columns = 2
                            val rows = ceil(groups.size / columns.toDouble()).toInt()
                            val totalGaps = gap * (rows - 1)
                            var cellHeight = (maxHeight - totalGaps) / rows
                            // Lower minimum height to fit all cards
                            cellHeight = cellHeight.coerceAtLeast(50.dp)
                            Log.i("MainScreen", "rows=$rows totalGaps=$totalGaps cellHeight=$cellHeight")

                            LazyVerticalGrid(
                                columns = GridCells.Fixed(columns),
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(gap),
                                verticalArrangement = Arrangement.spacedBy(gap),
                                userScrollEnabled = false,
                                content = {
                                    items(groups) { g ->
                                        val (color, bgColor) = getSizeColors(g.size)
                                        SizeCard(
                                            shortLabel = g.size.shortLabel,
                                            fullLabel = g.size.label,
                                            puzzleCount = g.totalPuzzles,
                                            progressPercent = g.progressPercent,
                                            color = color,
                                            backgroundColor = bgColor,
                                            onClick = { onGroupSelected(g.id) },
                                            isCompact = true,
                                            modifier = Modifier
                                                .height(cellHeight)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Получить цвета для размера головоломки
 */
private fun getSizeColors(size: PuzzleSize): Pair<androidx.compose.ui.graphics.Color, androidx.compose.ui.graphics.Color> {
    return when (size) {
        PuzzleSize.EXTRA_SMALL -> ColorXS to ColorXSLight
        PuzzleSize.SMALL -> ColorS to ColorSLight
        PuzzleSize.MEDIUM -> ColorM to ColorMLight
        PuzzleSize.LARGE -> ColorL to ColorLLight
        PuzzleSize.EXTRA_LARGE -> ColorXL to ColorXLLight
    }
}
