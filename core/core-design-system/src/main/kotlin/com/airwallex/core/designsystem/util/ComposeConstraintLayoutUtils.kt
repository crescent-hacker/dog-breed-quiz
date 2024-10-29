package com.airwallex.core.designsystem.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.airwallex.core.basic.model.AirwallexException

/**
 * used for passing constrained layout reference and anchors created in the parent Composable into sub Composable
 */
class ConstrainedLayoutAnchors private constructor(
    val selfReference: ConstrainedLayoutReference,
    private val _topAnchor: ConstraintLayoutBaseScope.HorizontalAnchor? = null,
    private val _bottomAnchor: ConstraintLayoutBaseScope.HorizontalAnchor? = null,
    private val _startAnchor: ConstraintLayoutBaseScope.VerticalAnchor? = null,
    private val _endAnchor: ConstraintLayoutBaseScope.VerticalAnchor? = null,
    val topMargin: Dp = 0.dp,
    val bottomMargin: Dp = 0.dp,
    val startMargin: Dp = 0.dp,
    val endMargin: Dp = 0.dp
) {
    val topAnchor get() = _topAnchor ?: throw ConstrainedLayoutAnchorNotFound("Top anchor")
    val bottomAnchor get() = _bottomAnchor ?: throw ConstrainedLayoutAnchorNotFound("Bottom anchor")
    val startAnchor get() = _startAnchor ?: throw ConstrainedLayoutAnchorNotFound("Start anchor")
    val endAnchor get() = _endAnchor ?: throw ConstrainedLayoutAnchorNotFound("End anchor")

    companion object {
        data class ConstrainedLayoutAnchorNotFound(
            private val anchorName: String
        ) : AirwallexException("$anchorName of constraint layout must not be null when being invoked.")

        fun ConstrainedLayoutReference.withAnchors(
            top: ConstraintLayoutBaseScope.HorizontalAnchor? = null,
            bottom: ConstraintLayoutBaseScope.HorizontalAnchor? = null,
            start: ConstraintLayoutBaseScope.VerticalAnchor? = null,
            end: ConstraintLayoutBaseScope.VerticalAnchor? = null,
            topMargin: Dp = 0.dp,
            bottomMargin: Dp = 0.dp,
            startMargin: Dp = 0.dp,
            endMargin: Dp = 0.dp,
        ): ConstrainedLayoutAnchors = ConstrainedLayoutAnchors(
            selfReference = this,
            _topAnchor = top,
            _bottomAnchor = bottom,
            _startAnchor = start,
            _endAnchor = end,
            topMargin = topMargin,
            bottomMargin = bottomMargin,
            startMargin = startMargin,
            endMargin = endMargin
        )
    }
}

fun ConstrainScope.linkAllTo(target: ConstrainedLayoutReference, margin: Dp = 0.dp, goneMargin: Dp = 0.dp, verticalBias: Float = 0.5f, horizontalBias: Float = 0.5f) {
    linkTo(start = target.start, end = target.end, startMargin = margin, startGoneMargin = goneMargin, endMargin = margin, endGoneMargin = goneMargin, bias = horizontalBias)
    linkTo(top = target.top, bottom = target.bottom, topMargin = margin, topGoneMargin = goneMargin, bottomMargin = margin, bottomGoneMargin = goneMargin, bias = verticalBias)
}

fun ConstrainScope.linkHorizontalTo(target: ConstrainedLayoutReference, margin: Dp = 0.dp, goneMargin: Dp = 0.dp, bias: Float = 0.5f) {
    linkTo(start = target.start, end = target.end, startMargin = margin, startGoneMargin = goneMargin, endMargin = margin, endGoneMargin = goneMargin, bias = bias)
}

fun ConstrainScope.linkVerticalTo(target: ConstrainedLayoutReference, margin: Dp = 0.dp, goneMargin: Dp = 0.dp, bias: Float = 0.5f) {
    linkTo(top = target.top, bottom = target.bottom, topMargin = margin, topGoneMargin = goneMargin, bottomMargin = margin, bottomGoneMargin = goneMargin, bias = bias)
}

fun ConstrainScope.fillSizeToConstraint() {
    width = Dimension.fillToConstraints
    height = Dimension.fillToConstraints
}

var ConstrainScope.isVisible
    get() = visibility == Visibility.Visible
    set(value) {
        visibility = if (value) Visibility.Visible else Visibility.Gone
    }

var ConstrainScope.isInvisible
    get() = visibility == Visibility.Invisible
    set(value) {
        visibility = if (value) Visibility.Invisible else Visibility.Visible
    }
