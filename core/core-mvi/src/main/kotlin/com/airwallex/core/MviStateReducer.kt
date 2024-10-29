package com.airwallex.core

fun interface MviStateReducer<EventResult : MviResult, ViewState : MviViewState> {
    /**
     * Given the previous [ViewState] and the new [EventResult] from an action, resolve and return a new [ViewState].
     * 1. The new [ViewState] might trigger recomposition on a Compose screen.
     * 2. Not necessarily each [EventResult] will trigger a [ViewState] change.
     *    E.g. An SaveTokenEvent will save a login token in KeyStore, but will not trigger any UI change.
     */
    fun reduce(previousViewState: ViewState, result: EventResult): ViewState
}
