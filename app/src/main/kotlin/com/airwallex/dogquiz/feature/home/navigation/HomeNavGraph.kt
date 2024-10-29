package com.airwallex.dogquiz.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.airwallex.core.util.EventBus
import com.airwallex.dogquiz.feature.home.ui.HomeScreen
import com.airwallex.dogquiz.navigation.AppNavRoute
import com.airwallex.dogquiz.navigation.handleAppNavIfNeeded
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.error.NavRouteActionNotFoundException
import com.airwallex.feature.shared.navigation.AbstractFeatureNavGraph
import com.airwallex.feature.shared.navigation.NavRoute
import com.airwallex.feature.shared.navigation.NavRoute.Companion.emptyNavRoute
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.navigation.composable
import com.airwallex.feature.shared.navigation.rememberOnNav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class HomeNavRoute(
    override val route: String,
    override val args: List<String> = emptyList(),
    override val deeplinks: List<NavDeepLink> = emptyList(),
) : NavRoute(route, args, deeplinks) {
    data object HomeRoot : HomeNavRoute(route = "home-root")
    data object Home : HomeNavRoute(route = "home")
}

sealed class HomeNavAction : ScreenNavAction {
}

object HomeNavGraph : AbstractFeatureNavGraph() {
    override val rootRoute: NavRoute = HomeNavRoute.HomeRoot
    override val startRoute: NavRoute = HomeNavRoute.Home

    override fun NavGraphBuilder.registerScreens(navController: NavController, appEventBus: EventBus<AppEvent>, scope: CoroutineScope) {
        composable(HomeNavRoute.Home) {
            val onNav: (ScreenNavAction) -> Unit =
                rememberOnNav(
                    navController = navController,
                    resolveNavRoute = { action ->
                        when (action) {
                            is HomeNavRoute -> action.destNavRoute
                            else -> handleAppNavIfNeeded(action, appEventBus, scope).emptyNavRoute()
                        }
                    })

            HomeScreen(
                onNav = onNav
            )
        }
    }
}
