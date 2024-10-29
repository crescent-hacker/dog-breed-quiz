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
    resourcePrefix = "com_airwallex_core_designsystem"
    namespace = "com.airwallex.core.designsystem"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = deps.versions.composeKotlinCompiler.get()
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    api(deps.bundles.compose)
    api(deps.bundles.composeAccompanist)
    api(deps.bundles.compose3rdPartyLibs)
    debugApi(deps.bundles.composeDebug)
}
