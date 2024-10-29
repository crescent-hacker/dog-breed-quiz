package com.airwallex.dogquiz.util

import com.airwallex.core.util.AppLogger
import com.airwallex.dogquiz.BuildConfig

const val isProduction = BuildConfig.FLAVOR_environment == "production"
val isProductionRelease = isProduction && !BuildConfig.DEBUG
val isProductionDebug = isProduction && BuildConfig.DEBUG
const val isSandbox = BuildConfig.FLAVOR_environment == "sandbox"

fun toVersionCode(appVersion: String): Int {
    val versionParts = appVersion.split(".")
    if (versionParts.size != 3) {
        AppLogger.error("Invalid version format: $appVersion")
        return 0
    }
    return versionParts[0].toInt() * 1000_000_000 + versionParts[1].toInt() * 1000_000 + versionParts[2].toInt() * 1000
}
