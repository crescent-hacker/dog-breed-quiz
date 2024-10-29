@file:Suppress("UnstableApiUsage")

import config.BuildDependencies
import config.LocaleConfig
import ext.bundle
import ext.version
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

println("INFO: feature module ${project.name} is loaded.")

plugins {
    `android-library`
    `kotlin-android`
    `kotlinx-serialization`
    org.jlleitschuh.gradle.ktlint
    `kotlin-kapt`
}

ktlint {
    disabledRules.addAll("no-wildcard-imports", "filename")
}

android {

    compileSdk = version(BuildDependencies.Versions.Keys.COMPILE_SDK).toInt()

    resourcePrefix = "com_airwallex_feature_"

    defaultConfig {
        minSdk = version(BuildDependencies.Versions.Keys.MIN_SDK).toInt()
        targetSdk = version(BuildDependencies.Versions.Keys.TARGET_SDK).toInt()

        resourceConfigurations += LocaleConfig.specifiedLanguageResources
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = BuildDependencies.Versions.javaSourceCompatibility
        targetCompatibility = BuildDependencies.Versions.javaTargetCompatibility
    }

    kotlinOptions {
        jvmTarget = version(BuildDependencies.Versions.Keys.JVM_TARGET)
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = version(BuildDependencies.Versions.Keys.COMPOSE_KOTLIN_COMPILER)
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    coreLibraryDesugaring(bundle(BuildDependencies.Bundles.Keys.JVM))

    api(project(":feature-shared"))

    testImplementation(bundle(BuildDependencies.Bundles.Keys.TEST))
    androidTestImplementation(bundle(BuildDependencies.Bundles.Keys.ANDROID_TEST))
}
