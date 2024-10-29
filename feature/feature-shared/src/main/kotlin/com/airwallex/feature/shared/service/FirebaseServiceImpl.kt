package com.airwallex.feature.shared.service

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val ctx: Context,
) : FirebaseService {

    private val firebaseCrashlytics get() = FirebaseCrashlytics.getInstance()
    private val firebaseAnalytics get() = FirebaseAnalytics.getInstance(ctx)

    override fun init() {
    }

    override fun setupUser(userId: String) {
        firebaseCrashlytics.setUserId(userId)
        firebaseAnalytics.setUserId(userId)
    }

    /**
     * Clear firebase local data
     *
     * Not gonna clear crashlytics on logout coz we want to record crash if previous user logout
     */
    override fun clearUser() {
//        firebaseAnalytics.setUserId(null)
    }
}
