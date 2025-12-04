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
                name = "Classic Light",
                primaryColor = 0xFF3F51B5,
                secondaryColor = 0xFF00BCD4,
                backgroundColor = 0xFFF5F7FA,
                isSelected = true
            ),
            AppTheme(
                id = 1,
                name = "Ocean Blue",
                primaryColor = 0xFF0277BD,
                secondaryColor = 0xFF00BCD4,
                backgroundColor = 0xFFE3F2FD,
                isSelected = false
            ),
            AppTheme(
                id = 2,
                name = "Forest Green",
                primaryColor = 0xFF2E7D32,
                secondaryColor = 0xFF66BB6A,
                backgroundColor = 0xFFE8F5E9,
                isSelected = false
            ),
            AppTheme(
                id = 3,
                name = "Sunset Orange",
                primaryColor = 0xFFE65100,
                secondaryColor = 0xFFFF9800,
                backgroundColor = 0xFFFFF3E0,
                isSelected = false
            ),
            AppTheme(
                id = 4,
                name = "Purple Dream",
                primaryColor = 0xFF6A1B9A,
                secondaryColor = 0xFFAB47BC,
                backgroundColor = 0xFFF3E5F5,
                isSelected = false
            ),
            AppTheme(
                id = 5,
                name = "Dark Mode",
                primaryColor = 0xFFBB86FC,
                secondaryColor = 0xFF03DAC6,
                backgroundColor = 0xFF121212,
                isSelected = false
            ),
            AppTheme(
                id = 6,
                name = "Candy Pink",
                primaryColor = 0xFFC2185B,
                secondaryColor = 0xFFF06292,
                backgroundColor = 0xFFFCE4EC,
                isSelected = false
            ),
            AppTheme(
                id = 7,
                name = "Mint Fresh",
                primaryColor = 0xFF00695C,
                secondaryColor = 0xFF26A69A,
                backgroundColor = 0xFFE0F2F1,
                isSelected = false
            ),
            AppTheme(
                id = 8,
                name = "Royal Purple",
                primaryColor = 0xFF5E35B1,
                secondaryColor = 0xFF9575CD,
                backgroundColor = 0xFFEDE7F6,
                isSelected = false
            ),
            AppTheme(
                id = 9,
                name = "Cherry Red",
                primaryColor = 0xFFC62828,
                secondaryColor = 0xFFEF5350,
                backgroundColor = 0xFFFFEBEE,
                isSelected = false
            ),
            AppTheme(
                id = 10,
                name = "Golden Yellow",
                primaryColor = 0xFFF57F17,
                secondaryColor = 0xFFFFD54F,
                backgroundColor = 0xFFFFFDE7,
                isSelected = false
            ),
            AppTheme(
                id = 11,
                name = "Midnight Blue",
                primaryColor = 0xFF0D47A1,
                secondaryColor = 0xFF42A5F5,
                backgroundColor = 0xFFE1F5FE,
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
