package com.airwallex.core.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun <I, O> rememberLambdaWithInput(lambda: (I) -> O) = remember { lambda }

@Composable
fun <I, O> rememberLambdaWithInput(
        key1: Any?,
        lambda: (I) -> O
) = remember(key1) { lambda }

@Composable
fun <I : Any, O> rememberLambdaWithInput(
        key1: Any?,
        key2: Any?,
        lambda: (I) -> O
) = remember(key1, key2) { lambda }

@Composable
fun <I, O> rememberLambdaWithInput(
        key1: Any?,
        key2: Any?,
        key3: Any?,
        lambda: (I) -> O
) = remember(key1, key2, key3) { lambda }

@Composable
fun <I, O> rememberLambdaWithInput(
        vararg keys: Any?,
        lambda: (I) -> O
) = remember(*keys) { lambda }

@Composable
fun <O> rememberLambda(lambda: () -> O) = remember { lambda }

@Composable
fun <O> rememberLambda(
        key1: Any?,
        lambda: () -> O
) = remember(key1) { lambda }

@Composable
fun <O> rememberLambda(
        key1: Any?,
        key2: Any?,
        lambda: () -> O
) = remember(key1, key2) { lambda }

@Composable
fun <O> rememberLambda(
        key1: Any?,
        key2: Any?,
        key3: Any?,
        lambda: () -> O
) = remember(key1, key2, key3) { lambda }

@Composable
fun <T, O> rememberLambdaWithArguments(
    key1: Any?,
    key2: Any?,
    key3: Any?,
    lambda: (T) -> O
) = remember(key1, key2, key3) { lambda }

@Composable
fun <O> rememberLambda(
        vararg keys: Any?,
        lambda: () -> O
) = remember(*keys) { lambda }

@Composable
fun <O> rememberComposableLambda(
        key1: Any?,
        key2: Any?,
        key3: Any?,
        lambda: @Composable () -> O
) = remember(key1, key2, key3) { lambda }

@Composable
fun <O> rememberComposableLambda(
        vararg keys: Any?,
        lambda: @Composable () -> O
) = remember(*keys) { lambda }

/**
 * enhanced version of [rememberUpdatedState], added [onValueChange] to notify value change
 */
@Composable
fun <T> rememberUpdatedState(newValue: T, onValueChange: (T) -> Unit = {}): State<T> = remember {
    mutableStateOf(newValue)
}.apply {
    if (value != newValue) {
        onValueChange(newValue)
    }
    value = newValue
}

/**
 * enhanced version of [rememberUpdatedState], added [onValueChange] to notify value change with old value
 */
@Composable
fun <T> rememberStateChanged(newValue: T, onValueChange: (T, T) -> Unit = { _: T, _: T -> }): State<T> = remember {
    mutableStateOf(newValue)
}.apply {
    if (value != newValue) {
        onValueChange(value, newValue)
    }
    value = newValue
}
