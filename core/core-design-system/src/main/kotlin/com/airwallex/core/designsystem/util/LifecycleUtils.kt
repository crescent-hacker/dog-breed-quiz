package com.airwallex.core.designsystem.util

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.Job

@Composable
fun RepeatWhenStarted(
        block: () -> Job
) {
    val isStarted = LocalLatestLifecycleEvent.current
            .takeIf { it != Lifecycle.Event.ON_ANY }
            ?.targetState == Lifecycle.State.STARTED

    DisposableEffect(isStarted) {
        val job = block()
        onDispose { job.cancel() }
    }
}

@Composable
fun RepeatWhenResumed(
        block: () -> Job
) {
    val isResumed = LocalLatestLifecycleEvent.current
            .takeIf { it != Lifecycle.Event.ON_ANY }
            ?.targetState == Lifecycle.State.RESUMED

    DisposableEffect(isResumed) {
        val job = block()
        onDispose { job.cancel() }
    }
}

@Composable
fun RepeatWhenCreated(
        block: () -> Job
) {
    val isCreated = LocalLatestLifecycleEvent.current
            .takeIf { it != Lifecycle.Event.ON_ANY }
            ?.targetState == Lifecycle.State.CREATED

    DisposableEffect(isCreated) {
        val job = block()
        onDispose { job.cancel() }
    }
}

@Composable
fun rememberLatestLifecycleEvent(): MutableState<Lifecycle.Event> {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val latestLifecycleEvent = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            latestLifecycleEvent.value = event
        }
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return latestLifecycleEvent
}

val LocalLatestLifecycleEvent = compositionLocalOf<Lifecycle.Event> {
    error("Lifecycle event is not provided yet.")
}

val Lifecycle.Event.isOnResume
    get() = this == Lifecycle.Event.ON_RESUME
val Lifecycle.Event.isOnCreate
    get() = this == Lifecycle.Event.ON_CREATE
val Lifecycle.Event.isOnStart
    get() = this == Lifecycle.Event.ON_START
val Lifecycle.Event.isOnPause
    get() = this == Lifecycle.Event.ON_PAUSE
val Lifecycle.Event.isOnStop
    get() = this == Lifecycle.Event.ON_STOP
val Lifecycle.Event.isOnDestroy
    get() = this == Lifecycle.Event.ON_DESTROY
val Lifecycle.Event.isCreated
    get() = this >= Lifecycle.Event.ON_CREATE && this < Lifecycle.Event.ON_DESTROY
val Lifecycle.Event.isStarted
    get() = this >= Lifecycle.Event.ON_START && this <= Lifecycle.Event.ON_STOP
val Lifecycle.Event.isResumed
    get() = this == Lifecycle.Event.ON_RESUME
