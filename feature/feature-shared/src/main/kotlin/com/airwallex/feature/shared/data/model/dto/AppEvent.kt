package com.airwallex.feature.shared.data.model.dto

import com.airwallex.core.util.GlobalEvent
import com.airwallex.core.util.TextHolder
import com.airwallex.feature.shared.navigation.NavRoute
import com.airwallex.feature.shared.navigation.ScreenNavAction

/**
 * Global event that need handled by Main activity
 */
sealed class AppEvent : GlobalEvent {
    data object RestartActivityEvent : AppEvent()
    data object ResetAllRepoCacheEvent : AppEvent()
    data class ToastEvent(val text: TextHolder, val isLong: Boolean = false) : AppEvent()
    data class NavigateEvent(val navRoute: NavRoute) : AppEvent() {
        companion object {
            val NavigateBackEvent get() = NavigateEvent(NavRoute.BACK)
        }
    }
}





