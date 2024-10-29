package ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.support.delegates.ProjectDelegate

/**
 * the name "deps" here need to be the same as the name in ${rootDir}/settings.gradle.kts
 */
val Project.versionCatalog: VersionCatalog get() =
    rootProject.extensions.getByType<VersionCatalogsExtension>().named("deps")

fun VersionCatalog.version(versionKey: String) = this.findVersion(versionKey).get().requiredVersion
fun VersionCatalog.bundle(bundleKey: String) = this.findBundle(bundleKey).get()
fun VersionCatalog.plugin(pluginKey: String) = this.findPlugin(pluginKey).get()
fun VersionCatalog.library(libraryKey: String) = this.findLibrary(libraryKey).get()

fun Project.version(versionKey: String) = this.versionCatalog.version(versionKey)
fun Project.bundle(bundleKey: String) = this.versionCatalog.bundle(bundleKey)
fun Project.plugin(pluginKey: String) = this.versionCatalog.plugin(pluginKey)
fun Project.library(libraryKey: String) = this.versionCatalog.library(libraryKey)

fun ProjectDelegate.version(versionKey: String) = this.project.version(versionKey)
fun ProjectDelegate.bundle(bundleKey: String) = this.project.bundle(bundleKey)
fun ProjectDelegate.plugin(pluginKey: String) = this.project.plugin(pluginKey)
fun ProjectDelegate.library(libraryKey: String) = this.project.library(libraryKey)

