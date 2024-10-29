package com.airwallex.core.basic.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its result under a condition
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> T.runIf(predicate: (T) -> Boolean, block: T.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (predicate(this)) {
        block()
    } else
        this
}

/**
 * Calls the specified function [block] with `this` value as its receiver and returns its result under a condition
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> T.runIf(condition: Boolean, block: T.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (condition) {
        this.block()
    } else
        this
}

inline fun <reified T> Any.asTypeOrNull(): T? = this as? T
inline fun <reified T> Any.asType(): T = this as T

infix fun <A, B> Pair<A, B>.and(pair: Pair<A, B>) =
    listOf(this, pair)

infix fun <A, B> List<Pair<A, B>>.and(pair: Pair<A, B>) =
    listOf(this, pair)
