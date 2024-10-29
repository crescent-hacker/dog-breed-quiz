@file:Suppress("UnstableApiUsage")

/**
 * plugin management
 */
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

/**
 * dependencies management
 */
dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = uri("https://jitpack.io" ))
    }

    versionCatalogs {
        create("buildDeps") {
            from(files("../dependencies.toml"))
        }
    }
}
