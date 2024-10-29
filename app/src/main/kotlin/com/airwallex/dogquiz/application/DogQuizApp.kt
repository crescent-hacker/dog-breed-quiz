package com.airwallex.dogquiz.application

import android.app.Application
import com.airwallex.core.util.*
import com.airwallex.dogquiz.data.datastore.AppPreferencesDataStore
import com.airwallex.dogquiz.error.FirebaseErrorReporter
import com.airwallex.feature.shared.di.ApplicationCoroutineScope
import com.airwallex.feature.shared.service.FirebaseService
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


@Suppress("DEPRECATION")
@HiltAndroidApp
class DogQuizApp : Application() {
    init {
        AppLogger = getLogger(LOGGER_TAG, com.airwallex.dogquiz.BuildConfig.FLAVOR_environment, BuildConfig.DEBUG)
    }

    @Inject
    lateinit var firebaseService: FirebaseService

    @Inject
    lateinit var appPreferencesDataStore: AppPreferencesDataStore


    @[Inject ApplicationCoroutineScope]
    lateinit var appScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        setupErrorReporter() // this must be setup first
        setupFirebase()

        AppLogger.info("App initialization completed")
    }

    /**************************************************
     *    setup 3rd party integration services        *
     **************************************************/
    private fun setupFirebase() {
        firebaseService.init()

        // Firebase installation ID
        if (appPreferencesDataStore.deviceID.isBlank()) {
            FirebaseInstallations.getInstance().id.addOnCompleteListener {
                if (it.isSuccessful) {
                    appPreferencesDataStore.deviceID = it.result
                } else {
                    appPreferencesDataStore.deviceID = resolveAndroidId()
                }
            }
        }
    }

    private fun setupErrorReporter() {
        ErrorReportUtils.init(FirebaseErrorReporter)
    }
}

