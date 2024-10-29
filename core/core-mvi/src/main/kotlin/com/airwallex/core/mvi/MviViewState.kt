package com.airwallex.core.mvi

import com.airwallex.core.mvi.MviViewState.Status
import com.airwallex.core.mvi.MviViewState.Status.*
import kotlin.reflect.KClass

/**
 * View State in MVI architecture
 * 1. It should represent all states of a screen including view data, view status of UI components.
 * 2. The root view state represents the whole screen's states.
 * 3. When UI component requires complex UI presentation, you should create more sub-states:
 *  E.g.
 *      sealed class DemoScreenViewState(override val uiStatus: UIStatus) : MviViewState {
 *          object ScreenLoading : DemoScreenViewState(LOADING)
 *
 *          data class ScreenContent(
 *              val submitButtonState: SubmitButtonState,
 *              val heading: String,
 *              val content: String
 *          ) : DemoScreenViewState(CONTENT)
 *
 *          data class ScreenError(val errorTitle: String, val errorMessage: String): DemoScreenViewState(ERROR)
 *
 *          object ScreenIdle: DemoScreenViewState(IDLE)
 *      }
 *
 *      sealed class SubmitButtonState {
 *          object Loading : SubmitButtonState()
 *          class Visible(text: String, isEnabled: Boolean) : SubmitButtonState()
 *          object Hidden : SubmitButtonState()
 *      }

 * 4. It should be the main and the only output from a ViewModel and used by Compose to declare a screen.
 */
interface MviViewState {
    val status: Status

    /**
     * UI status for a screen, it's either:
     * 1. LOADING - api or async task is running, will need to show loader and wait
     * 2. CONTENT - content data of the screen is ready, show the content that describes the screen
     * 3. ERROR - screen content data is failed to fetch, need to show error state
     * 4. IDLE - default status, could be used when have a static screen, or when the screen is not supposed to start
     *           loading or show content at the beginning.
     */
    enum class Status {
        LOADING, CONTENT, ERROR, IDLE
    }
}

infix fun KClass<*>.or(type: KClass<*>): List<KClass<*>> =
    listOf(this, type)

infix fun MviViewState.either(viewStateType: List<KClass<*>>): Boolean =
    viewStateType.any { this::class == it }

/**
 * Generic implementation of mvi view state for a simple screen.
 */
sealed class SimpleMviViewState(
    override val status: Status
) : MviViewState {
    object Loading : SimpleMviViewState(status = LOADING)

    data class Content<T>(val data: T) : SimpleMviViewState(status = CONTENT)

    data class Error(val errorMessage: String) : SimpleMviViewState(status = ERROR)

    object Idle : SimpleMviViewState(status = IDLE)
}
