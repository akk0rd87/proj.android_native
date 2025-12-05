package com.jcross.prototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.model.CellState
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.theme.*

/**
 * Экран игры - японский кроссворд
 */
@Composable
fun GameScreen(
    groupId: Int,
    folderId: Int,
    puzzleId: Int,
    onBackClick: () -> Unit,
    onImmersiveModeChange: (Boolean) -> Unit = {}
) {
    val folders = remember { MockDataProvider.getFoldersForGroup(groupId) }
    val folder = folders.getOrNull(folderId) ?: return
    val puzzles = remember { MockDataProvider.getPuzzlesForFolder(folderId, folder.totalPuzzles) }
    val puzzle = puzzles.getOrNull(puzzleId) ?: return

    val gameField = remember { MockDataProvider.getGameField(puzzle) }
    val cells = remember { mutableStateOf(gameField.cells) }

    // Enable immersive mode when entering game screen
    DisposableEffect(Unit) {
        onImmersiveModeChange(true)
        onDispose {
            onImmersiveModeChange(false)
        }
    }

    Scaffold(
        topBar = {
            JCrossTopBar(
                title = puzzle.name,
                onBackClick = onBackClick
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Game grid (simplified visualization)
            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth(0.9f),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        for (row in 0 until gameField.height) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                for (col in 0 until gameField.width) {
                                    GameCell(
                                        state = cells.value[row][col],
                                        onClick = {
                                            val newCells = cells.value.map { it.clone() }.toTypedArray()
                                            newCells[row][col] = when (cells.value[row][col]) {
                                                CellState.EMPTY -> CellState.FILLED
                                                CellState.FILLED -> CellState.MARKED
                                                CellState.MARKED -> CellState.EMPTY
                                            }
                                            cells.value = newCells
                                        },
                                        modifier = Modifier.weight(1f).aspectRatio(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Hints
            Text(
                text = "Tap: Fill • Double tap: Mark as empty",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* TODO: Undo */ },
                    colors = ButtonDefaults.buttonColors(containerColor = TextSecondary)
                ) {
                    Text("Undo")
                }

                Button(
                    onClick = { /* TODO: Hint */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Hint")
                }

                Button(
                    onClick = { /* TODO: Check */ },
                    colors = ButtonDefaults.buttonColors(containerColor = ColorS)
                ) {
                    Text("Check")
                }
            }
        }
    }
}

@Composable
fun GameCell(
    state: CellState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                when (state) {
                    CellState.EMPTY -> Surface
                    CellState.FILLED -> Primary
                    CellState.MARKED -> ColorXL.copy(alpha = 0.3f)
                }
            )
            .border(1.dp, DividerColor, RoundedCornerShape(4.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (state == CellState.MARKED) {
            Text(
                text = "×",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ColorXL
            )
        }
    }
}
