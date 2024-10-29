package com.airwallex.core.mvi

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.Flow

/**
 * MVI view model for Compose with MVI architecture
 */
interface MviViewModel<
        Event : MviEvent,
        ViewState : MviViewState,
        ViewEffect : MviViewEffect,
        EventResult : MviResult> {

    /**
     * entry point for Compose to send event to trigger view state change or side effect
     */
    fun dispatchEvent(event: Event)

    /**
     * entry point for Compose to send view side effect straightaway when no event processing needed
     */
    fun dispatchSideEffect(viewEffect: ViewEffect, delayMillis: Long = 0L)

    /**
     * view state source for Compose to subscribe, new view state emission might trigger recomposition if data changed
     */
    val viewState: State<ViewState>

    /**
     * UI side effect - one-time actions that should impact the UI
     * e.g. triggering a navigation action, showing a Toast, SnackBar etc.
     *
     * 1. Events can fire off side-effects
     * 2. Side-effects can fire events.
     *
     * @see <a href="https://developer.android.com/jetpack/compose/side-effects>Side Effects</a>
     */
    val viewEffects: Flow<ViewEffect>

    /**
     * MVI Contract - declares necessary mvi components includes:
     * 1. eventFilter (optional)
     * 2. eventProcessor
     * 3. stateReducer
     * 4. effectMapper (optional)
     */
    fun getContract(): MviContract<Event, ViewState, ViewEffect, EventResult>
}
