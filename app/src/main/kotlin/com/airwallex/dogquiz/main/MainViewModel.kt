package com.airwallex.dogquiz.main

import androidx.lifecycle.ViewModel
import com.airwallex.core.util.EventBus
import com.airwallex.core.util.TextHolder
import com.airwallex.dogquiz.data.datastore.AppPreferencesDataStore
import com.airwallex.dogquiz.di.AppMoshi
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.di.ApplicationCoroutineScope
import com.airwallex.feature.shared.util.launch
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationCoroutineScope private val appScope: CoroutineScope,
    @AppMoshi private val moshi: Moshi,
    private val appEventBus: EventBus<AppEvent>,
    private val appPreferencesDataStore: AppPreferencesDataStore,
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
    }

    suspend fun toast(text: TextHolder) = appEventBus.dispatch(AppEvent.ToastEvent(text))
    suspend fun longToast(text: TextHolder) = appEventBus.dispatch(AppEvent.ToastEvent(text, isLong = true))

    /**
     * Pops back stack of nav graph (consumed in AppNavGraph)
     */
    fun navigateBack() {
        launch {
            appEventBus.dispatch(AppEvent.NavigateEvent.NavigateBackEvent)
        }
    }

    fun getAppThemeModeFlow() = appPreferencesDataStore.watchAppThemeMode
    fun getAppThemeMode() = appPreferencesDataStore.appThemeMode
}

