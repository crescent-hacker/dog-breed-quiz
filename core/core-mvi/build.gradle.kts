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
    resourcePrefix = "com_airwallex_core_mvi_"
    namespace = "com.airwallex.core.mvi"

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
}
