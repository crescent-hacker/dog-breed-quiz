package com.airwallex.dogquiz.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.airwallex.core.designsystem.theme.LocalAppThemeMode
import com.airwallex.core.designsystem.theme.LocalNightMode
import com.airwallex.core.designsystem.theme.applyAppDefaultNightMode
import com.airwallex.core.designsystem.theme.isDisplayNightMode
import com.airwallex.core.designsystem.util.LocalLatestLifecycleEvent
import com.airwallex.core.designsystem.util.LocalWindowSizeClass
import com.airwallex.core.designsystem.util.rememberLatestLifecycleEvent
import com.airwallex.core.designsystem.util.rememberSystemBarPaddings
import com.airwallex.core.designsystem.util.rememberWindowSizeClass
import com.airwallex.core.designsystem.util.saveSystemBarPaddings
import com.airwallex.core.util.EventBus
import com.airwallex.dogquiz.ui.theme.DogQuizAppTheme
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.di.ApplicationCoroutineScope
import com.airwallex.feature.shared.di.LocalAppEventBus
import com.airwallex.feature.shared.di.LocalMoshiInstance
import com.airwallex.feature.shared.ui.base.AbstractActivity
import com.airwallex.feature.shared.util.launch
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AbstractActivity() {
    @Inject
    override lateinit var appEventBus: EventBus<AppEvent>

    @Inject
    lateinit var moshi: Moshi

    @[Inject ApplicationCoroutineScope]
    lateinit var appScope: CoroutineScope

    @Inject
    lateinit var appEventProcessor: AppEventProcessor

    private val rootView get() = this.findViewById<View>(android.R.id.content)
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        subscribeLifecycleEvents()

        setContent {
            val windowSizeClass = rememberWindowSizeClass()
            val latestLifecycleEvent by rememberLatestLifecycleEvent()
            val currentThemeModeState by mainViewModel.getAppThemeModeFlow().collectAsState(initial = mainViewModel.getAppThemeMode())
            val systemBarPaddings by rememberSystemBarPaddings()

            LaunchedEffect(currentThemeModeState) {
                applyAppDefaultNightMode(currentThemeModeState)
            }

            LaunchedEffect(systemBarPaddings) {
                if (systemBarPaddings.hasValues()) {
                    this@MainActivity.saveSystemBarPaddings(systemBarPaddings)
                }
            }

            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass,
                LocalMoshiInstance provides moshi,
                LocalLatestLifecycleEvent provides latestLifecycleEvent,
                LocalAppThemeMode provides currentThemeModeState,
                LocalNightMode provides isDisplayNightMode(currentThemeModeState),
                LocalAppEventBus provides appEventBus
            ) {
                DogQuizAppTheme {
                    MainScreen(viewModel = mainViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    /************************************************************************************
     *                                      Helpers                                     *
     ************************************************************************************/

    /**
     * handle activity scope actions triggered by lifecycle event
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun subscribeLifecycleEvents() {
        val activity = this

        launch {
            appEventBus.eventFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect {
                    appEventProcessor.process(it)
                }
        }
    }

    /************************************************************************************
     *                                 Activity lifecycle                               *
     ************************************************************************************/

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // a trick to prevent UI state being restored in a dilemma state for the case that app from being killed by system when app is in background
        outState.clear()
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onActivityResult(requestCode, resultCode, data)",
        "com.airwallex.feature.shared.ui.base.AbstractActivity"
    )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
