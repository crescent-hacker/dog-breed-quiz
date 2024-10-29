package com.airwallex.core.designsystem.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.airwallex.core.util.TextHolder

@Composable
fun @receiver:StringRes Int.stringArrayRes(): Array<String> =
        stringArrayResource(this)

@Composable
fun @receiver:StringRes Int.stringRes(): String =
        stringResource(this)

@Composable
fun @receiver:StringRes Int.stringRes(vararg formatArgs: Any): String =
        stringResource(this, *formatArgs)

@Composable
fun TextHolder.resolve(): String =
        textRes.takeIf { it != 0 }?.stringRes()
                ?: text

@Composable
fun TextHolder.resolveOrNull(): String? =
        textRes.takeIf { it != 0 }?.stringRes()
                ?: text.takeIf { it.isNotEmpty() }

@Composable
fun TextHolder.resolveOrDefault(default: String = ""): String =
        textRes.takeIf { it != 0 }?.stringRes()
                ?: text.takeIf { text.isNotEmpty() }
                ?: default

fun @receiver:StringRes Int.wrap() = TextHolder(textRes = this)
fun String.wrap() = TextHolder(text = this)
