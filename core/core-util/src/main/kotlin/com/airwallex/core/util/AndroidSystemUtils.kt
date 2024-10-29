package com.airwallex.core.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.provider.Settings

const val platformName: String = "android"
const val googleWalletPackageName = "com.google.android.apps.walletnfcrel"
const val googlePayServiceName = "com.google.android.gms.tapandpay.hce.service.TpHceService"
const val googleWalletPlayStoreUrl =
    "http://play.google.com/store/apps/details?id=$googleWalletPackageName"

@Deprecated("Use Firebase installation id to replace android ID, see https://firebase.google.com/docs/reference/android/com/google/firebase/iid/FirebaseInstanceId")
@SuppressLint("HardwareIds")
fun Context.resolveAndroidId(): String =
    Settings.Secure.getString(
        contentResolver,
        Settings.Secure.ANDROID_ID
    )

val deviceIdentifier: String get() = "${Build.MANUFACTURER},${Build.MODEL}"

val osVersion: String
    get() = Build.VERSION.RELEASE.let {
        if (!it.contains(".")) "$it.0" else it
    }

val osVersionLessThanAndroidN: Boolean get() = Build.VERSION.SDK_INT < N

val isGooglePixelDevice: Boolean
    get() = Build.MANUFACTURER.equals("Google", true) && Build.MODEL.startsWith("Pixel", true)
val isGooglePixelOneDevice: Boolean
    get() = Build.MANUFACTURER.equals("Google", true) &&
        (Build.MODEL.equals("Pixel", true) || Build.MODEL.equals("Pixel XL", true))
val isXiaomiDevice: Boolean get() = Build.MANUFACTURER.equals("Xiaomi", true)

@Suppress("DEPRECATION")
fun Context.isAppInstalled(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

/*************** system services definitions ********************/

inline fun <reified T> Context.systemService(): T? =
    try {
        with(applicationContext) {
            getSystemService(T::class.java)
        }
    } catch (e: ClassNotFoundException) {
        null
    }
