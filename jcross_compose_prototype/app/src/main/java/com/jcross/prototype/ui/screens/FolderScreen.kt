package com.jcross.prototype.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.components.PuzzleGridCard

/**
 * Экран списка головоломок в папке
 */
@Composable
fun FolderScreen(
    groupId: Int,
    folderId: Int,
    onBackClick: () -> Unit,
    onPuzzleSelected: (Int) -> Unit
) {
    val folders = remember { MockDataProvider.getFoldersForGroup(groupId) }
    val folder = folders.getOrNull(folderId) ?: return

    val puzzles = remember { MockDataProvider.getPuzzlesForFolder(folderId, folder.totalPuzzles) }

    Scaffold(
        bottomBar = {
            JCrossTopBar(
                title = "${folder.width}x${folder.height}",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 80.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(puzzles) { index, puzzle ->
                PuzzleGridCard(
                    puzzleNumber = index + 1,
                    isSolved = puzzle.isSolved,
                    isInProgress = false,
                    onClick = { onPuzzleSelected(index) }
                )
            }
        }
    }
}
