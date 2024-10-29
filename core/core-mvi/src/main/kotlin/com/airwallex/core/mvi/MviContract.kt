package com.airwallex.core.mvi

import kotlinx.coroutines.flow.FlowCollector

/**
 * A Contract to aggregate key MVI components
 * 1. [MviEvent], [MviResult], [MviViewEffect], [MviViewState] should be declared in the contract.
 * 2. [eventProcessor] and [stateReducer] must be declared in the contract.
 * 3. [effectMapper] is optional if no need to use side-effect, and should use [EmptyViewEffect] as generic type.
 * 4. [eventFilter] is optional, it could be overridden when you need to put conditions on even filtering.
 */
abstract class MviContract<
    Event : MviEvent,
    ViewState : MviViewState,
    ViewEffect : MviViewEffect,
    EventResult : MviResult> {
    /**
     * customize event filter in order to pre-processing incoming events.
     * e.g. only accept initialize event once:
     *      ObservableTransformer<BrowseIntent, BrowseIntent> {
     *          it.publish {
     *              Observable.merge(it.ofType(InitialEvent::class.java).take(1),
     *                              it.filter({ intent -> intent !is InitialEvent }))
     *          }
     *      }
     */
    open val eventFilter: suspend FlowCollector<Event>.(Event) -> Unit = { rawEvent ->
        emit(rawEvent)
    }

    abstract val eventProcessor: MviEventProcessor<Event, EventResult>

    abstract val stateReducer: MviStateReducer<EventResult, ViewState>

    open val effectMapper: MviEffectMapper<EventResult, ViewEffect>? = null
}
