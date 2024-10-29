package com.airwallex.feature.shared.navigation

import com.airwallex.feature.shared.navigation.NavRoute.Companion.emptyNavRoute

/**
 * Screen navigation action
 */
interface ScreenNavAction {
    val destNavRoute: NavRoute
        get() = NavRoute.emptyNavRoute()
}

object NavigateBack : ScreenNavAction
