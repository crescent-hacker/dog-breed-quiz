package com.airwallex.feature.shared.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.airwallex.core.util.EventBus
import com.airwallex.feature.shared.data.model.dto.AppEvent
import kotlinx.coroutines.CoroutineScope

abstract class AbstractFeatureNavGraph {
    /**
     * the route that represents the root of this feature nav graph
     */
    abstract val rootRoute: NavRoute

    /**
     * the route for the start screen
     */
    abstract val startRoute: NavRoute

    /**
     * register the defined graph, usually should be called in app module when assemble the app nav graph
     */
    fun registerNavigation(
        navGraphBuilder: NavGraphBuilder,
        navController: NavController,
        appEventBus: EventBus<AppEvent>,
        scope: CoroutineScope
    ) = with(navGraphBuilder) {
        navigation(
            rootRoute = rootRoute,
            startDestination = startRoute,
        ) {
            registerScreens(navController = navController, appEventBus = appEventBus, scope = scope)
        }
    }

    /**
     * for nav graph sub class to impl, define screens and how they nav to each other
     */
    abstract fun NavGraphBuilder.registerScreens(
        navController: NavController,
        appEventBus: EventBus<AppEvent>,
        scope: CoroutineScope
    )
}
