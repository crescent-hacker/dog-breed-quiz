package com.airwallex.core.basic.module

/**
 * initialise module with module config
 */
interface ModuleInitializer<T : ModuleConfig> {
    fun init(moduleConfig: T)
}
