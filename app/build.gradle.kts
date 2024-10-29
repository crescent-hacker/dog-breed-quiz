@file:Suppress("UnstableApiUsage", "EnumValuesSoftDeprecate")

import config.ProductFlavor
import config.ProductFlavor.Environment
import config.ProductFlavor.Mode
import config.ProjectConfig.Companion.config
import config.ProjectConfig.ModuleType.APP
import config.SigningConfig
import config.buildConfigFieldFromSystemEnv
import org.gradle.configurationcache.extensions.capitalized

config {
    moduleType = APP
}

// https://youtrack.jetbrains.com/issue/KTIJ-19369.
@Suppress(
    "DSL_SCOPE_VIOLATION", "MISSING_DEPENDENCY_CLASS", "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    id(deps.plugins.android.application.getPluginId())
    id(deps.plugins.kotlin.android.getPluginId())
    id(deps.plugins.firebase.crashlytics.getPluginId())
    id(deps.plugins.google.services.getPluginId())
    id(deps.plugins.kotlin.kapt.getPluginId())
    id(deps.plugins.ksp.getPluginId())
    id(deps.plugins.dagger.hilt.getPluginId())
    id(deps.plugins.kotlin.serialization.getPluginId())
}

android {
    val appVersion = config.AppVersion(rootProject.file("version.properties"))

    namespace = "com.airwallex.dogquiz"

    compileSdk = deps.versions.compileSdk.getInt()

    defaultConfig {
        applicationId = "com.airwallex.dogquiz"
        minSdk = deps.versions.minSdk.getInt()
        targetSdk = deps.versions.targetSdk.getInt()
        versionCode = appVersion.code
        versionName = appVersion.name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        resourceConfigurations += config.LocaleConfig.specifiedLanguageResources
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = config.BuildDependencies.Versions.javaSourceCompatibility
        targetCompatibility = config.BuildDependencies.Versions.javaTargetCompatibility
    }

    kotlinOptions {
        jvmTarget = deps.versions.jvmTarget.get()
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-XXLanguage:+NewInference"
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = deps.versions.composeKotlinCompiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/main.kotlin_module"
            // https://github.com/Kotlin/kotlinx.coroutines/issues/1059
            excludes += "META-INF/atomicfu.kotlin_module"
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    lint {
        disable += "Instantiatable"
    }

    signingConfigs {
        SigningConfig.values().forEach { config ->
            try {
                val createdSigningConfig = findByName(config.qualifier) ?: create(config.qualifier)

                createdSigningConfig.apply {
                    this.keyAlias = config.keyAlias
                    this.storePassword = config.storePassword
                    this.keyPassword = config.keyPassword
                    this.storeFile = file(config.storeFilePath)
                }
            } catch (e: Exception) {
                if (!config.skipErrors) {
                    throw e
                } else {
                    println("INFO: Error ${e.message} has been skipped for ${config.qualifier} signing config.")
                }
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            manifestPlaceholders["crashlyticsCollectionEnabled"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            enableUnitTestCoverage = false // TODO, config jacoco rules then enable coverage
            enableAndroidTestCoverage = false
//            isPseudoLocalesEnabled = true
            signingConfig = signingConfigs.getByName(config.SigningConfig.DEBUG.qualifier)
            manifestPlaceholders["crashlyticsCollectionEnabled"] = false
        }
    }

    setFlavorDimensions(ProductFlavor.Dimension.values().map { it.qualifier })

    productFlavors {
        Environment.values().forEach { env ->
            create(env.qualifier) {
                dimension = env.dimension.qualifier
                applicationId = env.applicationId
                signingConfig = signingConfigs.getByName(env.signingConfig.qualifier)
            }
        }

        Mode.values().forEach { mode ->
            create(mode.qualifier) {
                dimension = mode.dimension.qualifier
            }
        }
    }

    sourceSets {
        // setup sourceSets for debug build variants
        Mode.values().forEach { mode ->
            Environment.values().forEach { env ->
                create("${mode.qualifier}${env.qualifier.capitalized()}Debug") {
                    kotlin.srcDir("src/${env.qualifier}Debug/kotlin")
                    java.srcDir("src/${env.qualifier}Debug/java")
                    res.srcDir("src/${env.qualifier}Debug/res")
                }
            }
        }

        getByName("test") {
            kotlin.srcDir("src/test/kotlin")
            java.srcDir("src/test/java")
        }
    }

    splits {
        abi {
            isEnable = true
            reset()
            include("x86", "x86_64", "arm64-v8a", "armeabi-v7a")
            isUniversalApk = false
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

hilt {
    // enable to make transitive DI works, see https://medium.com/mobile-app-development-publication/dagger-hilt-on-multi-module-android-app-26815c427fb
    enableAggregatingTask = true
}

dependencies {
    coreLibraryDesugaring(deps.bundles.jvm)

    // DI
    implementation(deps.dagger.hilt)
    kapt(deps.dagger.hilt.compiler)

    // firebase
    implementation(platform(deps.firebase.bom))
    implementation(deps.bundles.firebase)

    // androidx
    implementation(deps.bundles.androidx)

    // compose
    implementation(deps.androidx.core.splashscreen)
    debugImplementation(deps.bundles.composeDebug)

    // material (for activity theme)
    implementation(deps.material.ui)

    // test
    testImplementation(deps.bundles.test)
    kaptTest(deps.dagger.hilt.compiler)

    // android test
    androidTestImplementation(deps.bundles.androidTest)
    kaptAndroidTest(deps.dagger.hilt.compiler)

    // feature modules
    rootProject.childProjects
        .filterKeys { it.startsWith("feature-") && it != "feature-core" }
        .forEach {
            implementation(it.value)
        }
}