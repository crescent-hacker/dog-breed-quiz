import config.BuildDependencies.Versions
import org.gradle.kotlin.dsl.`java-library`
import org.gradle.kotlin.dsl.kotlin

println("INFO: kotlin module ${project.name} is loaded.")

plugins {
    `java-library`
    kotlin("jvm")
}

java {
    sourceCompatibility = Versions.javaSourceCompatibility
    targetCompatibility = Versions.javaTargetCompatibility

    sourceSets {
        main {
            java.srcDir("src/main/kotlin")
        }
        test {
            java.srcDir("src/test/kotlin")
        }
    }
}
