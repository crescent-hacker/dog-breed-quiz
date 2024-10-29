package com.airwallex.dogquiz.error

import android.annotation.SuppressLint
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.airwallex.dogquiz.BuildConfig
import com.airwallex.core.util.ErrorReporter
import com.airwallex.core.util.AirwallexLogger
import com.airwallex.dogquiz.util.isProductionRelease

object FirebaseErrorReporter : ErrorReporter {
    @SuppressLint("LogNotTimber")
    override fun reportNonFatalException(e: Throwable, tag: String?, logger: AirwallexLogger) {
        if (!BuildConfig.DEBUG || isProductionRelease) {
            FirebaseCrashlytics.getInstance().recordException(e)
        } else {
            logger.error(e.message ?: "", e)
        }
    }
}
