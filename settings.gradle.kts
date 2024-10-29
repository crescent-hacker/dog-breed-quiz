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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven(url = uri("https://jitpack.io"))

        flatDir {
            dirs("libs")
        }
    }

    versionCatalogs {
        create("deps") {
            from(files("./dependencies.toml"))
        }
    }
}

/**
 * module management
 */
rootProject.name = "dog-breed-quiz"

// including feature/infra modules
listOf("feature", "core").forEach { projectsDir ->
    file(projectsDir).listFiles()?.filter { it.isDirectory }?.forEach { dir ->
        include(dir.name)
        project(":${dir.name}").projectDir = dir
    }
}
include(":app")
