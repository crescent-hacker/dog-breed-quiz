@file:Suppress("UnstableApiUsage")

import config.OKHTTPCLIENT_VERBOSE_LOG_ENABLED
import config.ProjectConfig.Companion.config
import config.ProjectConfig.ModuleType.CORE
import config.buildConfigFieldFromSystemEnv

// https://youtrack.jetbrains.com/issue/KTIJ-19369.
@Suppress(
    "DSL_SCOPE_VIOLATION", "MISSING_DEPENDENCY_CLASS", "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    id(deps.plugins.android.library.getPluginId())
    id(deps.plugins.kotlin.android.getPluginId())
    id(deps.plugins.dagger.hilt.getPluginId())
    id(deps.plugins.kotlin.kapt.getPluginId())
}

config {
    moduleType = CORE
}

android {
    resourcePrefix = "com_airwallex_core_network_"
    namespace = "com.airwallex.core.network"

    defaultConfig {
        buildConfigFieldFromSystemEnv(OKHTTPCLIENT_VERBOSE_LOG_ENABLED)
    }
}

dependencies {
    api(deps.dagger.hilt)
    kapt(deps.dagger.hilt.compiler)

    // restful network libs
    api(platform(deps.okhttp.bom))
    api(deps.bundles.okhttp)
    api(deps.bundles.retrofit)

    // json parser
    api(deps.bundles.moshi)
    kapt(deps.moshi.kotlin.codegen)
}
