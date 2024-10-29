@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.LibraryDefaultConfig
import config.BRAZE_LOGGING_LEVEL
import config.BuildConfigField
import config.FEATURES_ALL_ENABLED
import config.FEATURE_FLAG_PROPERTY_PREFIX
import config.MVI_LOGGING
import config.NAVIGATION_LOGGING
import config.ProjectConfig.Companion.config
import config.ProjectConfig.ModuleType.CORE
import config.buildConfigFieldFromSystemEnv
import ext.version
import org.jetbrains.kotlin.util.capitalizeDecapitalize.toUpperCaseAsciiOnly
import java.util.Properties

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
    resourcePrefix = "com_airwallex_feature_shared_"
    namespace = "com.airwallex.feature.shared"

    defaultConfig {
        vectorDrawables.useSupportLibrary = true

        buildConfigFieldFromSystemEnv(MVI_LOGGING)
        buildConfigFieldFromSystemEnv(NAVIGATION_LOGGING)
        buildConfigFieldFromSystemEnv(BRAZE_LOGGING_LEVEL)

        loadFeatureBuildConfigFields()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = version(config.BuildDependencies.Versions.Keys.COMPOSE_KOTLIN_COMPILER)
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    api(project(":core-datastore"))
    api(project(":core-network"))
    api(project(":core-mvi"))
    api(project(":core-design-system"))
    api(project(":core-util"))

    api(deps.bundles.googleplay)

    // image loader
    api(deps.bundles.coil)

    // firebase
    api(platform(deps.firebase.bom))
    api(deps.bundles.firebase)

    // hilt
    api(deps.dagger.hilt)
    kapt(deps.dagger.hilt.compiler)
}

/******************************************************************
 *                          helpers                               *
 ******************************************************************/

fun LibraryDefaultConfig.loadFeatureBuildConfigFields() {
    val featuresAllEnabled = FEATURES_ALL_ENABLED.getBooleanValueFromSystemEnv()

    val resolvedBuildConfigFields = mutableMapOf<String, BuildConfigField>()

    // read feature flag records from "feature-config.properties"
    rootProject.file("feature-config.properties").inputStream().use {
        val featureConfigProperties = Properties().apply { load(it) }
        featureConfigProperties
            .filter { entry ->
                // validate if key starts with prefix "com.airwallex.feature" and value type is Boolean
                (entry.key as? String)?.startsWith(FEATURE_FLAG_PROPERTY_PREFIX, true) == true &&
                    (entry.value as? String)?.isBoolean == true
            }
            .forEach {
                val buildConfigKey = (it.key as String)
                    .removePrefix(FEATURE_FLAG_PROPERTY_PREFIX)
                    .replace(".", "_")
                    .toUpperCaseAsciiOnly()
                    .let { key ->
                        "FEATURE_${key}_ENABLED"
                    }
                val buildConfigValue = (featuresAllEnabled || (it.value as String).toBoolean()).toString()

                resolvedBuildConfigFields[buildConfigKey] = BuildConfigField("boolean", buildConfigKey, buildConfigValue)
            }
    }

    // read feature flag records from "feature-config.local.properties", and locally override feature flags from "feature-config.properties"
    rootProject.file("feature-config.local.properties").takeIf { it.exists() }?.inputStream()?.use {
        val featureConfigProperties = Properties().apply { load(it) }
        featureConfigProperties
            .filter { entry ->
                // validate if key starts with prefix "com.airwallex.feature" and value type is Boolean
                (entry.key as? String)?.startsWith(FEATURE_FLAG_PROPERTY_PREFIX, true) == true &&
                    (entry.value as? String)?.isBoolean == true
            }
            .forEach {
                val buildConfigKey = (it.key as String)
                    .removePrefix(FEATURE_FLAG_PROPERTY_PREFIX)
                    .replace(".", "_")
                    .toUpperCaseAsciiOnly()
                    .let { key ->
                        "FEATURE_${key}_ENABLED"
                    }
                val buildConfigValue = it.value as String

                if (resolvedBuildConfigFields[buildConfigKey] != null) {
                    resolvedBuildConfigFields[buildConfigKey] = resolvedBuildConfigFields[buildConfigKey]!!.copy(defaultValue = buildConfigValue)
                }
            }
    }

    // declare buildConfigField in gradle
    resolvedBuildConfigFields.values.forEach {
        buildConfigField(it.type, it.name, it.defaultValue)
        println("DEBUG: feature flag - $it")
    }
}

/**
 * Resolve build config cache issue with Signed apk
 * TODO, could remove when updated to Gradle 8.1RC and above
 */
tasks.withType(Sign::class.java) {
    notCompatibleWithConfigurationCache("https://github.com/gradle/gradle/issues/13470")
}
