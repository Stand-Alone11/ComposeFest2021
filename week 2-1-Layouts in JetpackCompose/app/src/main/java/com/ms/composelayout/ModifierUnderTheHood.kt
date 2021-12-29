package com.ms.composelayout

import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.unit.*

/**
 * 안드로이드 컴포즈의 패딩 모디파이어 코드
 * 반드시 padding >= 0 이어야 한다.
 */
private class PaddingModifier(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val rtlAware: Boolean, // 오른쪽에서 왼쪽방향인 레이아웃? true : false
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {
    init {
        require(
            (start.value >= 0f || start == Dp.Unspecified) &&
                    (top.value >= 0f || top == Dp.Unspecified) &&
                    (end.value >= 0f || end == Dp.Unspecified) &&
                    (bottom.value >= 0f || bottom == Dp.Unspecified)
        ) {
            "Padding must be non-negative"
        }
    }

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        // child의 너비 + start + end
        // child의 높이 + top + bottom
        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height) {
            if (rtlAware) {
                // placeRelative()는 오른쪽에서 왼쪽 방향의 레이아웃에 대응 가능하다.
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + top.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + bottom.hashCode()
        result = 31 * result + rtlAware.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        val otherModifier = other as? PaddingModifier ?: return false
        return start == otherModifier.start &&
                top == otherModifier.top &&
                end == otherModifier.end &&
                bottom == otherModifier.bottom &&
                rtlAware == otherModifier.rtlAware
    }
}