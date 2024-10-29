package config

import assert
import config.ProjectConfig.ModuleType.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.extra

class ProjectConfig private constructor(
        var moduleType: ModuleType? = null,
        var isDemoModeEnabled: Boolean = false
) {
    enum class ModuleType {
        APP, FEATURE, CORE, KOTLIN
    }

    companion object {
        private const val PROJECT_CONFIG_KEY = "project_config"

        fun Project.config(
                projectConfigInitBlock: ProjectConfig.() -> Unit
        ) {
            assert(this != rootProject) {
                "ERROR: Project config must not apply on root project."
            }

            val projectConfig = ProjectConfig().apply { projectConfigInitBlock() }

            assert(projectConfig.moduleType != null) {
                "ERROR: Module type in project config must be defined, please check the build.gradle.kts of ${this.name}"
            }

            this.extra.set(PROJECT_CONFIG_KEY, projectConfig)

            when (projectConfig.moduleType) {
                APP -> {
                    // no need to create base.gradle as only one main app module will exist
                }
                FEATURE -> project.apply(plugin = "base-feature-module")
                CORE -> project.apply(plugin = "base-core-module")
                KOTLIN -> project.apply(plugin = "base-kotlin-module")
                else -> Unit
            }
        }

        val Project.moduleType: ModuleType
            get() {
                checkProjectConfig()
                return (this.extra.get(PROJECT_CONFIG_KEY) as ProjectConfig).moduleType!!
            }
        val Project.isDemoModeEnabled: Boolean
            get() {
                checkProjectConfig()
                return (this.extra.get(PROJECT_CONFIG_KEY) as ProjectConfig).isDemoModeEnabled
            }

        private fun Project.checkProjectConfig() {
            assert(this.extra.get(PROJECT_CONFIG_KEY) != null) {
                "Project config cannot be null, please check the build.gradle.kts of ${this.name}"
            }
        }
    }
}
