@file:Suppress("UnstableApiUsage", "DEPRECATION")

import config.BuildDependencies.Bundles
import config.BuildDependencies.Versions
import config.LocaleConfig
import ext.bundle
import ext.version
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

println("INFO: core module ${project.name} is loaded.")

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

    compileSdk = version(Versions.Keys.COMPILE_SDK).toInt()

    resourcePrefix = "com_airwallex_core_"

    defaultConfig {
        minSdk = version(Versions.Keys.MIN_SDK).toInt()
        targetSdk = version(Versions.Keys.TARGET_SDK).toInt()

        resourceConfigurations += LocaleConfig.specifiedLanguageResources

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

        sourceCompatibility = Versions.javaSourceCompatibility
        targetCompatibility = Versions.javaTargetCompatibility
    }

    kotlinOptions {
        jvmTarget = version(Versions.Keys.JVM_TARGET)
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    coreLibraryDesugaring(bundle(Bundles.Keys.JVM))

    api(project(":basic"))
    if(project.name != "core-util") {
        api(project(":core-util"))
    }

    testImplementation(bundle(Bundles.Keys.TEST))
    androidTestImplementation(bundle(Bundles.Keys.ANDROID_TEST))
}
