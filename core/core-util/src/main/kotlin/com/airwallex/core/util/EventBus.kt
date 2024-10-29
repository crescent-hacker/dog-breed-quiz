package com.airwallex.core.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class EventBus<T : GlobalEvent> @Inject constructor() {
    private val _eventFlow = MutableSharedFlow<T>(extraBufferCapacity = Int.MAX_VALUE)

    val eventFlow: SharedFlow<T> = _eventFlow.asSharedFlow()

    suspend fun dispatch(event: T) =
            _eventFlow.emit(event)
}

interface GlobalEvent {
    interface Processor<T : GlobalEvent> {
        fun process(event: T)
    }
}

