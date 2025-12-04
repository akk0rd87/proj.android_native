package com.jcross.prototype.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jcross.prototype.data.MockDataProvider
import com.jcross.prototype.model.Folder
import com.jcross.prototype.ui.components.FolderItem
import com.jcross.prototype.ui.components.JCrossTopBar

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
        topBar = {
            JCrossTopBar(
                title = "${group.size.label} Puzzles",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(folders) { index, folder ->
                FolderItem(
                    name = folder.name,
                    totalPuzzles = folder.totalPuzzles,
                    solvedPuzzles = folder.solvedPuzzles,
                    progressPercent = folder.progressPercent,
                    onClick = { onFolderSelected(index) }
                )
            }
        }
    }
}
