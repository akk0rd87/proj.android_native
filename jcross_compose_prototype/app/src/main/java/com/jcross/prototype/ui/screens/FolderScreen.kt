package com.jcross.prototype.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.components.PuzzleItem

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
        topBar = {
            JCrossTopBar(
                title = folder.name,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(puzzles) { index, puzzle ->
                PuzzleItem(
                    name = puzzle.name,
                    size = "${puzzle.width}×${puzzle.height}",
                    isSolved = puzzle.isSolved,
                    timeSpent = puzzle.timeSpent.takeIf { it > 0 },
                    onClick = { onPuzzleSelected(index) }
                )
            }
        }
    }
}
