package com.airwallex.feature.shared.data.repository.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.core.network.repository.BaseRepository
import com.airwallex.core.util.EventBus


@OptIn(DelicateCoroutinesApi::class)
abstract class PublicRepository(
    eventBus: EventBus<AppEvent>
) : BaseRepository() {
    init {
        GlobalScope.launch {
            // clear caches when logout
            eventBus.eventFlow
                .filter { it is AppEvent.ResetAllRepoCacheEvent }
                .collectLatest {
                    clearAll()
                }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
abstract class SecureRepository(
    eventBus: EventBus<AppEvent>
) : BaseRepository() {
    init {
        GlobalScope.launch {
            // clear caches when logout
            eventBus.eventFlow
                .filter { it is AppEvent.ResetAllRepoCacheEvent }
                .collectLatest {
                    clearAll()
                }
        }
    }
}
