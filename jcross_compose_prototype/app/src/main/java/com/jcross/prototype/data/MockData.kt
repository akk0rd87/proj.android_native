package com.jcross.prototype.data

import com.jcross.prototype.model.*

/**
 * Провайдер тестовых данных для прототипа
 * TODO: Заменить на вызовы JNI для интеграции с C++ кодом
 */
object MockDataProvider {

    /**
     * Получить список всех групп (размеров)
     */
    fun getGroups(): List<Group> {
        return listOf(
            Group(
                id = 0,
                size = PuzzleSize.EXTRA_SMALL,
                totalPuzzles = 2823,
                solvedPuzzles = 0
            ),
            Group(
                id = 1,
                size = PuzzleSize.SMALL,
                totalPuzzles = 9738,
                solvedPuzzles = 0
            ),
            Group(
                id = 2,
                size = PuzzleSize.MEDIUM,
                totalPuzzles = 11165,
                solvedPuzzles = 0
            ),
            Group(
                id = 3,
                size = PuzzleSize.LARGE,
                totalPuzzles = 25629,
                solvedPuzzles = 0
            ),
            Group(
                id = 4,
                size = PuzzleSize.EXTRA_LARGE,
                totalPuzzles = 4480,
                solvedPuzzles = 0
            )
        )
    }

    /**
     * Получить папки для группы
     */
    fun getFoldersForGroup(groupId: Int): List<Folder> {
        val folderCount = when (groupId) {
            0 -> 15  // XS
            1 -> 25  // S
            2 -> 30  // M
            3 -> 40  // L
            4 -> 20  // XL
            else -> 10
        }

        return List(folderCount) { index ->
            Folder(
                id = index,
                name = "Folder ${index + 1}",
                totalPuzzles = (50..200).random(),
                solvedPuzzles = (0..10).random()
            )
        }
    }

    /**
     * Получить головоломки для папки
     */
    fun getPuzzlesForFolder(folderId: Int, totalCount: Int): List<Puzzle> {
        return List(totalCount) { index ->
            val size = when ((5..25).random()) {
                in 5..10 -> 5 to 5
                in 11..15 -> 10 to 10
                in 16..20 -> 15 to 15
                else -> 20 to 20
            }

            Puzzle(
                id = index,
                name = "Puzzle ${index + 1}",
                width = size.first,
                height = size.second,
                isSolved = index < 3, // Первые 3 решены для примера
                timeSpent = if (index < 3) (60..600).random() else 0,
                bestTime = if (index < 3) (60..600).random() else null
            )
        }
    }

    /**
     * Получить игровое поле для головоломки
     */
    fun getGameField(puzzle: Puzzle): GameField {
        // Создаем простой пример 5x5
        val width = 5
        val height = 5

        // Пустое поле
        val cells = Array(height) { Array(width) { CellState.EMPTY } }

        // Подсказки для строк (пример: сколько закрашенных ячеек подряд)
        val rowHints = listOf(
            listOf(2, 1),
            listOf(1, 1),
            listOf(5),
            listOf(1, 1),
            listOf(2, 1)
        )

        // Подсказки для столбцов
        val columnHints = listOf(
            listOf(2, 1),
            listOf(1, 2),
            listOf(5),
            listOf(1, 2),
            listOf(2, 1)
        )

        return GameField(
            width = width,
            height = height,
            cells = cells,
            rowHints = rowHints,
            columnHints = columnHints
        )
    }

    /**
     * Получить доступные темы
     */
    fun getThemes(): List<AppTheme> {
        return listOf(
            AppTheme(
                id = 0,
                name = "Default",
                primaryColor = 0xFF3F51B5,
                secondaryColor = 0xFF00BCD4,
                backgroundColor = 0xFFF5F7FA,
                isSelected = true
            ),
            AppTheme(
                id = 1,
                name = "Dark",
                primaryColor = 0xFF1A1A1A,
                secondaryColor = 0xFF00F5FF,
                backgroundColor = 0xFF0F0F1E,
                isSelected = false
            ),
            AppTheme(
                id = 2,
                name = "Ocean",
                primaryColor = 0xFF2196F3,
                secondaryColor = 0xFF00BCD4,
                backgroundColor = 0xFFE3F2FD,
                isSelected = false
            ),
            AppTheme(
                id = 3,
                name = "Forest",
                primaryColor = 0xFF4CAF50,
                secondaryColor = 0xFF8BC34A,
                backgroundColor = 0xFFE8F5E9,
                isSelected = false
            ),
            AppTheme(
                id = 4,
                name = "Sunset",
                primaryColor = 0xFFFF6B6B,
                secondaryColor = 0xFFFFE66D,
                backgroundColor = 0xFFFFF3E0,
                isSelected = false
            )
        )
    }

    /**
     * Получить текущие настройки
     */
    fun getSettings(): AppSettings {
        return AppSettings(
            soundEnabled = true,
            musicEnabled = true,
            vibrateEnabled = true,
            showTimer = true,
            showErrors = true,
            autoFillEnabled = false,
            selectedThemeId = 0
        )
    }
}
