package com.airwallex.core.basic.model

open class AirwallexException(message: String?, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
    constructor(cause: Throwable) : this(cause.message, cause)
}
