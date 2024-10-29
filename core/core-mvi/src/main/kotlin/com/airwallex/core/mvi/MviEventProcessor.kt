package com.airwallex.core.mvi

import kotlinx.coroutines.flow.Flow

fun interface MviEventProcessor<Event : MviEvent, EventResult : MviResult> {
    /**
     * Given the previous [Event], process and return the [EventResult].
     * 1. Process approach could be a remote api call or an async task.
     *    E.g.
     *          loginRepository.
     *              .login(event.email, event.password)
     *              .mapToLoginResult()
     *              .startWith(LoginResult(IN_FLIGHT)) // to trigger loading viewState immediately
     *
     * 2. Process approach could be just an local validation or save data into local cache.
     *     E.g.
     *          Validate(event.email)
     *              .asObservable() // convert a plain result to Observable and no need for loading viewState initially
     *              .mapToValidateResult()
     *
     */
    fun process(event: Event): Flow<EventResult>
}
