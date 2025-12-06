package com.jcross.prototype.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.model.Folder
import com.jcross.prototype.ui.components.FolderGridCard
import com.jcross.prototype.ui.components.JCrossTopBar
import com.jcross.prototype.ui.utils.swipeToBack

/**
 * Экран списка папок в группе (по размеру)
 */
@Composable
fun GroupScreen(
    groupId: Int,
    onBackClick: () -> Unit,
    onFolderSelected: (Int) -> Unit
) {
    val folders = remember { MockDataProvider.getFoldersForGroup(groupId) }
    val group = remember { MockDataProvider.getGroups()[groupId] }

    Scaffold(
        bottomBar = {
            JCrossTopBar(
                title = group.size.label,
                onBackClick = onBackClick
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = Color.Transparent  // Make scaffold transparent
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 90.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(com.jcross.prototype.ui.theme.BackgroundGradient)  // Apply gradient
                .padding(paddingValues)
                .swipeToBack(onSwipeRight = onBackClick),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(folders) { index, folder ->
                FolderGridCard(
                    width = folder.width,
                    height = folder.height,
                    totalPuzzles = folder.totalPuzzles,
                    solvedPuzzles = folder.solvedPuzzles,
                    onClick = { onFolderSelected(index) }
                )
            }
        }
    }
}
