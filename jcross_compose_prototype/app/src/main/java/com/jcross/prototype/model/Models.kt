package com.jcross.prototype.model

/**
 * Размер головоломки
 */
enum class PuzzleSize(val label: String, val shortLabel: String) {
    EXTRA_SMALL("Extra Small", "XS"),
    SMALL("Small", "S"),
    MEDIUM("Medium", "M"),
    LARGE("Large", "L"),
    EXTRA_LARGE("Extra Large", "XL")
}

/**
 * Группа головоломок (категория по размеру)
 */
data class Group(
    val id: Int,
    val size: PuzzleSize,
    val totalPuzzles: Int,
    val solvedPuzzles: Int,
    val folders: List<Folder> = emptyList()
) {
    val progressPercent: Float
        get() = if (totalPuzzles > 0) solvedPuzzles.toFloat() / totalPuzzles else 0f
}

/**
 * Папка с головоломками
 */
data class Folder(
    val id: Int,
    val name: String,
    val totalPuzzles: Int,
    val solvedPuzzles: Int,
    val puzzles: List<Puzzle> = emptyList(),
    val width: Int = 0,  // Width of puzzles in this folder (multiples of 5)
    val height: Int = 0  // Height of puzzles in this folder (multiples of 5)
) {
    val progressPercent: Float
        get() = if (totalPuzzles > 0) solvedPuzzles.toFloat() / totalPuzzles else 0f
}

/**
 * Головоломка
 */
data class Puzzle(
    val id: Int,
    val name: String,
    val width: Int,
    val height: Int,
    val isSolved: Boolean
)

/**
 * Состояние ячейки в головоломке
 */
enum class CellState {
    EMPTY,      // Пустая
    FILLED,     // Закрашенная
    MARKED      // Отмеченная крестиком (точно пустая)
}

/**
 * Игровое поле
 */
data class GameField(
    val width: Int,
    val height: Int,
    val cells: Array<Array<CellState>>,
    val rowHints: List<List<Int>>,      // Подсказки для строк
    val columnHints: List<List<Int>>    // Подсказки для столбцов
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameField

        if (width != other.width) return false
        if (height != other.height) return false
        if (!cells.contentDeepEquals(other.cells)) return false
        if (rowHints != other.rowHints) return false
        if (columnHints != other.columnHints) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + cells.contentDeepHashCode()
        result = 31 * result + rowHints.hashCode()
        result = 31 * result + columnHints.hashCode()
        return result
    }
}

/**
 * Тема оформления
 */
data class AppTheme(
    val id: Int,
    val name: String,
    val primaryColor: Long,
    val secondaryColor: Long,
    val backgroundColor: Long,
    val isSelected: Boolean = false
)

/**
 * Настройки приложения
 */
data class AppSettings(
    val soundEnabled: Boolean = true,
    val musicEnabled: Boolean = true,
    val vibrateEnabled: Boolean = true,
    val showTimer: Boolean = true,
    val showErrors: Boolean = true,
    val autoFillEnabled: Boolean = false,
    val selectedThemeId: Int = 0
)
