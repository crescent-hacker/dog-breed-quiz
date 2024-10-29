package com.airwallex.core.designsystem.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.basic.ext.runIf
import com.airwallex.core.designsystem.theme.Dimens
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.theme.iconBoundaryPrimary
import com.airwallex.core.designsystem.theme.surfaceVariant
import com.airwallex.core.designsystem.util.MultipleEventsCutter
import com.airwallex.core.designsystem.util.get

@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    innerContainerModifier: Modifier = Modifier.padding(Dimens.Global.MediumPadding.get()),
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    AirwallexCustomCard(
        modifier = modifier,
        innerContainerModifier = innerContainerModifier,
        isSelected = isSelected,
        onClick = { multipleEventsCutter.processEvent(onClick) },
        enabled = enabled,
        minHeight = Dimens.Global.MediumLargePadding.get(),
        backgroundColor = backgroundColor,
        content = content,
    )
}


@Composable
fun SecondaryCard(
    modifier: Modifier = Modifier,
    innerContainerModifier: Modifier = Modifier.padding(Dimens.Global.MediumPadding.get()),
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.surfaceVariant,
    content: @Composable () -> Unit,
) {
    AirwallexCustomCard(
        modifier = modifier,
        innerContainerModifier = innerContainerModifier,
        isSelected = isSelected,
        onClick = onClick,
        enabled = enabled,
        minHeight = Dimens.Global.XXLargePadding.get(),
        backgroundColor = backgroundColor,
        content = content,
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AirwallexCustomCard(
    modifier: Modifier = Modifier,
    innerContainerModifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    minHeight: Dp = 0.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .heightIn(min = minHeight)
            .runIf(isSelected) {
                border(
                    width = 2.dp,
                    color = MaterialTheme.colors.iconBoundaryPrimary,
                    shape = MaterialTheme.customShapes.smallAllCornersRounded
                )
            }
            .composed { modifier },
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.customShapes.smallAllCornersRounded,
        backgroundColor = backgroundColor,
        elevation = 0.dp
    ) {
        Column(modifier = innerContainerModifier) {
            content()
        }
    }
}

@Composable
fun PrimarySurface(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    innerContainerModifier: Modifier = Modifier.padding(Dimens.Global.MediumPadding.get()),
    shape: Shape = MaterialTheme.customShapes.smallAllCornersRounded,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.runIf(isSelected) {
            border(
                width = 2.dp,
                color = MaterialTheme.colors.iconBoundaryPrimary,
                shape = shape
            )
        },
        shape = shape,
        elevation = 0.dp
    ) {
        Column(modifier = innerContainerModifier) {
            content()
        }
    }
}


@Composable
fun SecondarySurface(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    innerContainerModifier: Modifier = Modifier.padding(Dimens.Global.MediumPadding.get()),
    backgroundColor: Color = MaterialTheme.colors.surfaceVariant,
    shape: Shape = MaterialTheme.customShapes.smallAllCornersRounded,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.runIf(isSelected) {
            border(
                width = 2.dp,
                color = MaterialTheme.colors.iconBoundaryPrimary,
                shape = shape
            )
        },
        shape = shape,
        elevation = 0.dp,
        backgroundColor = backgroundColor,
    ) {
        Column(modifier = innerContainerModifier) {
            content()
        }
    }
}

@Composable
fun PrimaryCardPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    Card(
        modifier = modifier,
        shape = shape,
        elevation = 0.dp
    ) { }
}


@Composable
fun SecondaryCardPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
) {
    Card(
        modifier = modifier,
        shape = shape,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.surfaceVariant,
    ) { }
}
