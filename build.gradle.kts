@file:Suppress("UnstableApiUsage")


buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(deps.gradle.build.tools)
        classpath(deps.kotlin.gradle.plugin)
        classpath(deps.google.services.plugin)
        classpath(deps.ktlint.plugin)
        classpath(deps.jacoco.plugin)
        classpath(deps.owasp.dependency.check.plugin)
        classpath(deps.firebase.crashlytics.plugin)
        classpath(deps.dagger.hilt.gradle.plugin)
    }
}

@Suppress(
        "DSL_SCOPE_VIOLATION",
        "MISSING_DEPENDENCY_CLASS",
        "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
        "FUNCTION_CALL_EXPECTED"
)
plugins {
    alias(deps.plugins.gradle.versions)
    alias(deps.plugins.ksp)
    alias(deps.plugins.owasp.dependency.check)
}

subprojects {
    apply(plugin = rootProject.deps.plugins.ktlint.getPluginId())
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
