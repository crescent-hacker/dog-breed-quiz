@file:Suppress("UnstableApiUsage")

import config.ProjectConfig.Companion.config
import config.ProjectConfig.ModuleType.CORE

// https://youtrack.jetbrains.com/issue/KTIJ-19369.
@Suppress(
    "DSL_SCOPE_VIOLATION", "MISSING_DEPENDENCY_CLASS", "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    id(deps.plugins.android.library.getPluginId())
}

config {
    moduleType = CORE
}

android {
    resourcePrefix = "com_airwallex_core_util_"
    namespace = "com.airwallex.core.util"

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    api(deps.bundles.androidx)
    api(deps.bundles.moshi)

    api(deps.kotlin.reflect)
    api(deps.kotlinx.serialization)

    api(deps.google.guava)
}
