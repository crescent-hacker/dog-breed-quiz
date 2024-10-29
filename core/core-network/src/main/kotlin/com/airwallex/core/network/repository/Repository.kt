package com.airwallex.core.network.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import com.airwallex.core.network.repository.Repository.QueryArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import java.util.concurrent.atomic.AtomicBoolean

interface Repository {
    interface Cache<T> {
        /**
         * get cached data as flow
         * @param refresh force refresh data if true
         *
         * @return return result as a flow
         */
        fun getAsFlow(refresh: Boolean = false): Flow<Result<T>>

        fun clear()
    }

    interface QueryArgs

    /**
     * clear all data sources
     */
    fun clearAll()
}

/**
 * Use this when no args is needed in query
 */
object NoArgs : QueryArgs

abstract class BaseRepository : Repository {
    val caches: MutableMap<String, Repository.Cache<*>> = mutableMapOf()
    private val job = SupervisorJob()
    val scope = CoroutineScope(Dispatchers.IO + job)

    inline fun <reified T> cache(
        key: String = T::class.java.canonicalName ?: T::class.java.toString(),
        noinline query: (NoArgs) -> Flow<Result<T>>
    ): Repository.Cache<T> =
        cache(key, NoArgs, query)

    inline fun <reified QA : QueryArgs, reified T> cache(
        key: String = T::class.java.canonicalName ?: T::class.java.toString(),
        args: QA,
        noinline query: (QA) -> Flow<Result<T>>
    ): Repository.Cache<T> =
        CacheImpl(args, query).apply {
            caches[key] = this
        }

    override fun clearAll() {
        job.cancelChildren()
        caches.forEach {
            it.value.clear()
        }
    }
}

class CacheImpl<QA : QueryArgs, T>(
    private val args: QA,
    private val query: (QA) -> Flow<Result<T>>
) : Repository.Cache<T> {
    private var cacheFlow: MutableSharedFlow<Result<T>> = MutableSharedFlow(replay = 1)

    // a flag to control concurrency of query
    private var isInFlight = AtomicBoolean(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAsFlow(refresh: Boolean): Flow<Result<T>> {
        if (refresh || cacheFlow.replayCache.isEmpty() && !isInFlight.get()) {
            cacheFlow.resetReplayCache()
            isInFlight.set(true)

            return query(args)
                .onEach {
                    cacheFlow.emit(it)
                }
                .flatMapLatest {
                    cacheFlow
                }
                .onCompletion {
                    isInFlight.set(false)
                }
        }

        return cacheFlow
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun clear() {
        cacheFlow.resetReplayCache()
        isInFlight.set(false)
    }
}
