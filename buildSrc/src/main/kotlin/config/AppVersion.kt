package config

import assert
import getSystemEnvOrEmpty
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Max version code need to be less than 2100000000
 *
 * @see [Version your app](https://developer.android.com/studio/publish/versioning)
 */
data class AppVersion(private val versionPropertiesFile: File) {
    companion object {
        private const val DEFAULT_BUILD_NUM = 0

        private const val BUILD_NUM_RANGE = 10000
        private const val PATCH_NUM_RANGE = 1000000
        private const val MINOR_NUM_RANGE = 100000000
        private const val VERSION_CODE_MAX_NUM = 2100000000
        private const val VERSION_CODE_MIN_NUM = 0
    }

    private val keystoreProperties = Properties()

    init {
        versionPropertiesFile.inputStream().use {
            keystoreProperties.load(it)

            majorVersion = (keystoreProperties["majorVersion"] as String).toInt()
            minorVersion = (keystoreProperties["minorVersion"] as String).toInt()
            patchVersion = (keystoreProperties["patchVersion"] as String).toInt()
        }
    }

    private var majorVersion: Int = 0
    private var minorVersion: Int = 0
    private var patchVersion: Int = 0
    private val buildNumber = getSystemEnvOrEmpty("BUILD_NUMBER").let { buildNum ->
        if (buildNum.isBlank())
            DEFAULT_BUILD_NUM
        else
            buildNum.toInt() % BUILD_NUM_RANGE
    }

    /**
     * version code for build.gradle.kts to use
     */
    val code: Int
        get() {
            checkVersion()

            return majorVersion * MINOR_NUM_RANGE + minorVersion * PATCH_NUM_RANGE + patchVersion * BUILD_NUM_RANGE + buildNumber
        }

    /**
     * version name for build.gradle.kts to use
     */
    val name: String
        get() {
            checkVersion()

            return if (buildNumber == DEFAULT_BUILD_NUM)
                "${majorVersion}.${minorVersion}.${patchVersion}"
            else
                "${majorVersion}.${minorVersion}.${patchVersion}-b${buildNumber}"
        }

    /**
     * check version when version code is used
     */
    private fun checkVersion() {
        val code = majorVersion * MINOR_NUM_RANGE + minorVersion * PATCH_NUM_RANGE + patchVersion * BUILD_NUM_RANGE + buildNumber

        assert(code > VERSION_CODE_MIN_NUM) {
            "Version code is currently less than or equal to 0, " +
                    "need to review and fix it."
        }
        assert(code <= VERSION_CODE_MAX_NUM) {
            "Version code is currently greater than 2100000000 which violates google's limitation, " +
                    "need to review and fix it."
        }
    }
}
