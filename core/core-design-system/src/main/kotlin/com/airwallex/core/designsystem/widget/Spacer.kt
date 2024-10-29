package com.airwallex.core.designsystem.widget

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airwallex.core.designsystem.theme.Dimens

@Composable
fun HorizontalSpacer(modifier: Modifier = Modifier, width: Dp = Dimens.Global.MediumPadding.get()) = Spacer(modifier = modifier.width(width))

@Composable
fun MediumHorizontalSpacer(modifier: Modifier = Modifier) = HorizontalSpacer(modifier, Dimens.Global.MediumPadding.get())

@Composable
fun MediumLargeHorizontalSpacer(modifier: Modifier = Modifier) = HorizontalSpacer(modifier, Dimens.Global.MediumLargePadding.get())

@Composable
fun VerticalSpacer(modifier: Modifier = Modifier, height: Dp = Dimens.Global.MediumPadding.get()) = Spacer(modifier = modifier.height(height))

@Composable
fun MediumVerticalSpacer(modifier: Modifier = Modifier) = VerticalSpacer(modifier, Dimens.Global.MediumPadding.get())

@Composable
fun MediumLargeVerticalSpacer(modifier: Modifier = Modifier) = VerticalSpacer(modifier, Dimens.Global.MediumLargePadding.get())
