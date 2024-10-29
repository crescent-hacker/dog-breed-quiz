package com.airwallex.core.basic.module

/**
 * Context object to hold module internal shared information
 */
interface ModuleContext {
    /**
     * Used when need to identify the string/resources is module specific
     */
    val modulePrefix: String
}
