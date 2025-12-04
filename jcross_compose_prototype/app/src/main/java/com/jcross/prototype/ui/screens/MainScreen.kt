package com.jcross.prototype.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
        }
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
                    .padding(horizontal = 32.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "JCross Nonograms",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Select puzzle size",
                    fontSize = 16.sp,
                    color = TextSecondary
                )
            }

            // Size cards list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(groups) { group ->
                    val (color, bgColor) = getSizeColors(group.size)

                    SizeCard(
                        shortLabel = group.size.shortLabel,
                        fullLabel = group.size.label,
                        puzzleCount = group.totalPuzzles,
                        progressPercent = group.progressPercent,
                        color = color,
                        backgroundColor = bgColor,
                        onClick = { onGroupSelected(group.id) }
                    )
                }

                // Stats section at bottom
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Surface.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "TOTAL",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "${groups.sumOf { it.totalPuzzles }}",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "SOLVED",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextSecondary
                                )
                                Text(
                                    text = "${groups.sumOf { it.solvedPuzzles }}",
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ColorS
                                )
                            }
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
