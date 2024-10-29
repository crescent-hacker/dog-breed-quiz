package com.airwallex.core.mvi

fun interface MviEffectMapper<EventResult : MviResult, ViewEffect : MviViewEffect> {
    /**
     * Map an processed [EventResult] to [ViewEffect] for downstream to subscribe and perform one-time side-effect
     * (mostly should happen in Compose layer)
     */
    fun mapToEffect(result: EventResult): ViewEffect
}
