package com.jcross.prototype.ui.utils

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

/**
 * Модификатор для обработки свайпа вправо (жест назад)
 *
 * @param onSwipeRight Callback при свайпе вправо от левого края
 * @param threshold Минимальное расстояние свайпа в пикселях (по умолчанию 100)
 * @param edgeWidth Ширина зоны от края экрана для начала свайпа (по умолчанию 50dp в пикселях)
 */
fun Modifier.swipeToBack(
    onSwipeRight: () -> Unit,
    threshold: Float = 100f,
    edgeWidth: Float = 150f
): Modifier = this.pointerInput(Unit) {
    var startX = 0f
    var startY = 0f
    var isEdgeSwipe = false

    detectHorizontalDragGestures(
        onDragStart = { offset ->
            startX = offset.x
            startY = offset.y
            // Проверяем, что свайп начался от левого или правого края
            isEdgeSwipe = offset.x < edgeWidth || offset.x > size.width - edgeWidth
        },
        onDragEnd = {
            if (isEdgeSwipe) {
                val deltaX = 0f - startX
                // Свайп вправо от левого края
                if (startX < edgeWidth && deltaX > threshold) {
                    onSwipeRight()
                }
                // Свайп влево от правого края
                else if (startX > size.width - edgeWidth && deltaX < -threshold) {
                    onSwipeRight()
                }
            }
            isEdgeSwipe = false
        },
        onDragCancel = {
            isEdgeSwipe = false
        },
        onHorizontalDrag = { change, dragAmount ->
            if (isEdgeSwipe) {
                change.consume()
            }
        }
    )
}
