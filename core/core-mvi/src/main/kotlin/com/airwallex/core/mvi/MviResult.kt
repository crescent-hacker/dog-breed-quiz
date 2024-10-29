package com.airwallex.core.mvi

/**
 * An [MviResult] is generated after [MviEventProcessor.process] processed a [MviEvent], its content could be:
 * 1. Success or Failure api call response
 * 2. Success or Failure of a async task
 * 3. Boolean result for a local email validation
 * ...
 */
interface MviResult {
    val processStatus: ProcessStatus

    /**
     * Process status for an [MviEvent] action, it's either:
     * 1. IN_FLIGHT - still waiting for the result to come back
     * 2. SUCCESS - got an successful result
     * 3. FAILURE - got an failure result
     */
    enum class ProcessStatus {
        IN_FLIGHT, SUCCESS, FAILURE
    }
}

