package config

import org.gradle.api.JavaVersion

/**
 * creating this object to assist version catalog in buildSrc due to version catalog is not working properly with
 * precompiled kotlin DSL in buildSrc, hence cannot read the versions from dependencies.toml directly
 *
 * @note the versions need to be consistent with dependencies.toml
 */
object BuildDependencies {
    /**
     * Versions
     */
    object Versions {
        /**
         * version key that align with dependencies.toml version section
         */
        object Keys {
            const val KOTLIN = "kotlin"
            const val JVM_TARGET = "jvmTarget"
            const val COMPILE_SDK = "compileSdk"
            const val MIN_SDK = "minSdk"
            const val TARGET_SDK = "targetSdk"
            const val KTLINT = "ktlintPlugin"
            const val COMPOSE_KOTLIN_COMPILER = "composeKotlinCompiler"
        }

        val javaSourceCompatibility = JavaVersion.VERSION_17
        val javaTargetCompatibility = JavaVersion.VERSION_17
    }

    object Bundles {
        /**
         * bundle key that align with dependencies.toml bundle section
         */
        object Keys {
            const val TEST = "test"
            const val ANDROID_TEST = "androidTest"
            const val JVM = "jvm"
        }
    }
}

