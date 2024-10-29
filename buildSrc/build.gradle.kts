import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(buildDeps.gradle.build.tools)
    implementation(buildDeps.gradle.api.build.tools)
    implementation(buildDeps.kotlin.gradle.plugin)
    implementation(buildDeps.kotlin.stdlib.jdk8.plugin)
    implementation(buildDeps.ktlint.plugin)
    implementation(buildDeps.javapoet)
    implementation(buildDeps.kotlin.serialization)
}
