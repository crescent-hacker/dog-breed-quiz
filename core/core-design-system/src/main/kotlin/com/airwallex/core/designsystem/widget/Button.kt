package com.airwallex.core.designsystem.widget

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airwallex.core.basic.ext.runIf
import com.airwallex.core.designsystem.R
import com.airwallex.core.designsystem.component.ResponsiveText
import com.airwallex.core.designsystem.theme.AirwallexColors
import com.airwallex.core.designsystem.theme.Dimens
import com.airwallex.core.designsystem.theme.Dimens.Global.MediumPadding
import com.airwallex.core.designsystem.theme.buttonBackgroundPrimary
import com.airwallex.core.designsystem.theme.buttonBackgroundQuaternary
import com.airwallex.core.designsystem.theme.buttonBackgroundSecondary
import com.airwallex.core.designsystem.theme.buttonBackgroundTertiary
import com.airwallex.core.designsystem.theme.buttonBorderSecondary
import com.airwallex.core.designsystem.theme.buttonTextPrimary
import com.airwallex.core.designsystem.theme.buttonTextQuaternary
import com.airwallex.core.designsystem.theme.buttonTextSecondary
import com.airwallex.core.designsystem.theme.buttonTextTertiary
import com.airwallex.core.designsystem.theme.customShapes
import com.airwallex.core.designsystem.theme.subtitle3
import com.airwallex.core.designsystem.theme.textPrimary
import com.airwallex.core.designsystem.widget.ButtonSize.NORMAL
import com.airwallex.core.designsystem.widget.ButtonSize.SMALL

enum class ButtonSize {
    NORMAL, SMALL
}

/*******************************************************
 *                  Primary button group               *
 *******************************************************/

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes textIdRes: Int,
    @DrawableRes iconRes: Int? = null,
    buttonPadding: Boolean = true,
    loading: Boolean = false
) {
    PrimaryButton(
        onClick = onClick,
        size = size,
        enabled = enabled,
        modifier = modifier,
        text = stringResource(id = textIdRes),
        iconRes = iconRes,
        buttonPadding = buttonPadding,
        loading = loading
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    @DrawableRes iconRes: Int? = null,
    buttonPadding: Boolean = true,
    loading: Boolean = false
) {
    val textStyle = when (size) {
        NORMAL -> MaterialTheme.typography.subtitle3
        SMALL -> MaterialTheme.typography.body1
    }

    val buttonHeight = when (size) {
        NORMAL -> 50.dp
        SMALL -> 40.dp
    }

    val buttonHorizontalPadding = when (size) {
        NORMAL -> 16.dp
        SMALL -> 0.dp
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.buttonBackgroundPrimary,
            contentColor = MaterialTheme.colors.buttonTextPrimary,
            disabledBackgroundColor = MaterialTheme.colors.buttonBackgroundPrimary,
            disabledContentColor = MaterialTheme.colors.buttonTextPrimary.copy(alpha = ContentAlpha.disabled),
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
        content = {
            if (loading) {
                ButtonLoadingRow(buttonHeight, color = MaterialTheme.colors.buttonTextPrimary)
            } else {
                Row(
                    modifier = Modifier
                        .heightIn(min = buttonHeight)
                        .composed {
                            runIf(buttonPadding) {
                                padding(horizontal = buttonHorizontalPadding)
                            }
                        }
                ) {
                    iconRes?.let { res ->
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = "Primary button icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    ResponsiveText(
                        text = text,
                        style = textStyle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    )
}

/*******************************************************
 *                  Secondary button group             *
 *******************************************************/

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    trailingImageModifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes textIdRes: Int,
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingImageRes: Int? = null,
    backgroundColor: Color = AirwallexColors.Transparent,
    buttonPadding: Boolean = true,
    loading: Boolean = false
) {
    SecondaryButton(
        onClick = onClick,
        size = size,
        enabled = enabled,
        modifier = modifier,
        trailingImageModifier = trailingImageModifier,
        text = stringResource(id = textIdRes),
        leadingIconRes = leadingIconRes,
        trailingImageRes = trailingImageRes,
        backgroundColor = backgroundColor,
        buttonPadding = buttonPadding,
        loading = loading
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    trailingImageModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingImageRes: Int? = null,
    backgroundColor: Color = AirwallexColors.Transparent,
    buttonPadding: Boolean = true,
    buttonHorizontalPadding: Dp? = null,
    loading: Boolean = false
) {
    val textStyle = when (size) {
        NORMAL -> MaterialTheme.typography.subtitle3
        SMALL -> MaterialTheme.typography.body1
    }

    val buttonHeight = when (size) {
        NORMAL -> 50.dp
        SMALL -> 40.dp
    }

    val resolvedButtonHorizontalPadding = buttonHorizontalPadding ?: when (size) {
        NORMAL -> 16.dp
        SMALL -> 0.dp
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.buttonBorderSecondary.let {
                    if (enabled) it else it.copy(alpha = ContentAlpha.disabled)
                },
                shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded
            ),
        shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = MaterialTheme.colors.buttonTextSecondary,
            disabledBackgroundColor = MaterialTheme.colors.buttonBackgroundSecondary,
            disabledContentColor = MaterialTheme.colors.buttonTextSecondary.copy(alpha = ContentAlpha.disabled),
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
        content = {
            if (loading) {
                ButtonLoadingRow(buttonHeight = buttonHeight, color = MaterialTheme.colors.buttonTextSecondary)
            } else {
                Row(
                    modifier = Modifier
                        .heightIn(min = buttonHeight)
                        .composed {
                            runIf(buttonPadding) {
                                padding(horizontal = resolvedButtonHorizontalPadding)
                            }
                        }
                ) {
                    leadingIconRes?.let { res ->
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = "Secondary button trailing icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    ResponsiveText(
                        text = text,
                        style = textStyle,
                        modifier = textModifier.align(Alignment.CenterVertically)
                    )
                    trailingImageRes?.let { res ->
                        Image(
                            painter = painterResource(id = res),
                            contentDescription = "Secondary button leading icon",
                            modifier = trailingImageModifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun CustomSecondaryButton(
    modifier: Modifier = Modifier,
    trailingImageModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingImageRes: Int? = null,
    backgroundColor: Color = AirwallexColors.Transparent,
    buttonHorizontalPadding: Dp? = null,
    verticalPadding: Dp = 9.5.dp,
    loading: Boolean = false
) {
    val textStyle = when (size) {
        NORMAL -> MaterialTheme.typography.subtitle3
        SMALL -> MaterialTheme.typography.body1
    }

    val buttonHeight = when (size) {
        NORMAL -> 50.dp
        SMALL -> 40.dp
    }

    val resolvedButtonHorizontalPadding = buttonHorizontalPadding ?: MediumPadding.get()
    Surface(
        shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
        modifier = modifier
            .heightIn(min = buttonHeight)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.buttonBorderSecondary.let {
                    if (enabled) it else it.copy(alpha = ContentAlpha.disabled)
                },
                shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded
            )
            .clip(MaterialTheme.customShapes.fiftyPercentAllCornersRounded)
            .clickable {
                onClick.invoke()
            },
        color = backgroundColor
    ) {
        if (loading) {
            ButtonLoadingRow(buttonHeight = buttonHeight, color = MaterialTheme.colors.buttonTextSecondary)
        } else {
            Row(
                modifier = Modifier.padding(
                    horizontal = resolvedButtonHorizontalPadding,
                    vertical = verticalPadding
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Dimens.Global.SmallPadding.get(), Alignment.Start),
            ) {
                leadingIconRes?.let { res ->
                    Icon(
                        painter = painterResource(id = res),
                        contentDescription = "Secondary button trailing icon",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
                Text(
                    modifier = textModifier,
                    text = text,
                    color = MaterialTheme.colors.textPrimary,
                    style = textStyle
                )
                trailingImageRes?.let { res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = "Secondary button leading icon",
                        modifier = trailingImageModifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}

/*******************************************************
 *                  Tertiary button group              *
 *******************************************************/

@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes textIdRes: Int,
    @DrawableRes iconRes: Int? = null,
    buttonPadding: Boolean = true,
    loading: Boolean = false
) {
    TertiaryButton(
        onClick = onClick,
        size = size,
        enabled = enabled,
        modifier = modifier,
        text = stringResource(id = textIdRes),
        iconRes = iconRes,
        loading = loading
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun TertiaryButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    height: Dp? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    @DrawableRes iconRes: Int? = null,
    loading: Boolean = false
) {
    val textStyle = when (size) {
        NORMAL -> MaterialTheme.typography.subtitle3
        SMALL -> MaterialTheme.typography.body1.copy(lineHeight = 20.sp)
    }

    val buttonHeight = height ?: when (size) {
        NORMAL -> 50.dp
        SMALL -> 40.dp
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.buttonBackgroundTertiary,
            contentColor = MaterialTheme.colors.buttonTextTertiary,
            disabledBackgroundColor = MaterialTheme.colors.buttonBackgroundTertiary,
            disabledContentColor = MaterialTheme.colors.buttonTextTertiary.copy(alpha = ContentAlpha.disabled),
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
        content = {
            if (loading) {
                ButtonLoadingRow(buttonHeight = buttonHeight, color = MaterialTheme.colors.buttonTextTertiary)
            } else {
                Row(
                    modifier = contentModifier
                        .heightIn(min = buttonHeight)
                ) {
                    iconRes?.let { res ->
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = "Tertiary button icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    ResponsiveText(
                        text = text,
                        style = textStyle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    )
}

/*******************************************************
 *                Quaternary button group              *
 *******************************************************/

@Composable
fun QuaternaryButton(
    modifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    @StringRes textIdRes: Int,
    @DrawableRes iconRes: Int? = null,
    buttonPadding: Boolean = true,
    loading: Boolean = false
) {
    QuaternaryButton(
        onClick = onClick,
        size = size,
        enabled = enabled,
        modifier = modifier,
        text = stringResource(id = textIdRes),
        iconRes = iconRes,
        buttonPadding = buttonPadding,
        loading = loading
    )
}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun QuaternaryButton(
    modifier: Modifier = Modifier,
    size: ButtonSize = NORMAL,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
    @DrawableRes iconRes: Int? = null,
    buttonPadding: Boolean = true,
    loading: Boolean = false
) {
    val textStyle = when (size) {
        NORMAL -> MaterialTheme.typography.subtitle3
        SMALL -> MaterialTheme.typography.body1
    }

    val buttonHeight = when (size) {
        NORMAL -> 50.dp
        SMALL -> 40.dp
    }

    val buttonHorizontalPadding = when (size) {
        NORMAL -> 16.dp
        SMALL -> 0.dp
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.buttonBackgroundQuaternary,
            contentColor = MaterialTheme.colors.buttonTextQuaternary,
            disabledBackgroundColor = MaterialTheme.colors.buttonBackgroundQuaternary,
            disabledContentColor = MaterialTheme.colors.buttonTextQuaternary.copy(alpha = ContentAlpha.disabled),
        ),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
        content = {
            if (loading) {
                ButtonLoadingRow(buttonHeight, color = MaterialTheme.colors.buttonTextQuaternary)
            } else {
                Row(
                    modifier = Modifier
                        .heightIn(min = buttonHeight)
                        .composed {
                            runIf(buttonPadding) {
                                padding(horizontal = buttonHorizontalPadding)
                            }
                        }
                ) {
                    iconRes?.let { res ->
                        Icon(
                            painter = painterResource(id = res),
                            contentDescription = "Quaternary button icon",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    ResponsiveText(
                        text = text,
                        style = textStyle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    )
}

/*******************************************************
 *                  Misc buttons                       *
 *******************************************************/

@Composable
private fun ButtonLoadingRow(buttonHeight: Dp, color: Color) {
    Row(
        modifier = Modifier
            .height(buttonHeight),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Lottie(
            contentScale = ContentScale.Crop,
            color = color,
            rawLottieRes = R.raw.btn_loader,
            modifier = Modifier.padding(bottom = 10.dp),
            lottieModifier = Modifier
                .widthIn(max = 110.dp)
                .fillMaxHeight()
        )
    }
}

@SuppressLint("UnnecessaryComposedModifier")
@Composable
fun CirclePrimaryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.subtitle3,
    size: Dp = 50.dp,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.customShapes.fiftyPercentAllCornersRounded,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.buttonBackgroundPrimary,
            contentColor = MaterialTheme.colors.buttonTextPrimary,
            disabledBackgroundColor = MaterialTheme.colors.buttonBackgroundPrimary,
            disabledContentColor = MaterialTheme.colors.buttonTextPrimary.copy(alpha = ContentAlpha.disabled),
        ),
        elevation = ButtonDefaults.elevation(),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
        content = {
                Row(
                    modifier = Modifier
                        .heightIn(min = size)
                        .composed {
                            padding(horizontal = 16.dp)
                        }
                ) {
                    ResponsiveText(
                        text = text,
                        style = textStyle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
        }
    )

}

