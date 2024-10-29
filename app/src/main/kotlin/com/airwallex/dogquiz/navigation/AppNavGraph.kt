package com.airwallex.dogquiz.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavDeepLink
import androidx.navigation.NavHostController
import com.airwallex.core.util.EventBus
import com.airwallex.dogquiz.feature.home.navigation.HomeNavGraph
import com.airwallex.dogquiz.feature.quiz.navigation.QuizNavGraph
import com.airwallex.feature.shared.data.model.dto.AppEvent
import com.airwallex.feature.shared.di.LocalAppEventBus
import com.airwallex.feature.shared.navigation.NavHost
import com.airwallex.feature.shared.navigation.NavRoute
import com.airwallex.feature.shared.navigation.NavRoute.Companion.BACK
import com.airwallex.feature.shared.navigation.NavRoute.Companion.emptyNavRoute
import com.airwallex.feature.shared.navigation.NavigateBack
import com.airwallex.feature.shared.navigation.ScreenNavAction
import com.airwallex.feature.shared.navigation.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import kotlin.math.acos

sealed class AppNavRoute(
    override val route: String,
    override val args: List<String> = emptyList(),
    override val deeplinks: List<NavDeepLink> = emptyList()
) : NavRoute(route, args, deeplinks) {
    data object AppRoot : AppNavRoute(route = "app-root")
}

sealed class AppNavAction(override val destNavRoute: NavRoute) : ScreenNavAction {
    data object NavigateToQuiz : AppNavAction(destNavRoute = QuizNavGraph.rootRoute)
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val appEventBus = LocalAppEventBus.current
    val coroutineScope = rememberCoroutineScope()
    val startDestination = remember { HomeNavGraph.rootRoute }

    // register composable screens
    NavHost(
        navController = navController,
        startDestination = startDestination,
        route = AppNavRoute.AppRoot.route,
        modifier = modifier
    ) {
        HomeNavGraph.registerNavigation(navGraphBuilder = this, navController = navController, appEventBus = appEventBus, scope = coroutineScope)
        QuizNavGraph.registerNavigation(navGraphBuilder = this, navController = navController, appEventBus = appEventBus, scope = coroutineScope)
        // TODO: Add more feature nav graphs here
    }

    subscribeAppNavigationEvents(appEventBus, navController)
}

@SuppressLint("ComposableNaming")
@Composable
private fun subscribeAppNavigationEvents(
    appEventBus: EventBus<AppEvent>,
    navController: NavHostController,
) {
    LaunchedEffect(Unit) {
        appEventBus.eventFlow
            .filterIsInstance<AppEvent.NavigateEvent>()
            .collectLatest {
                when (it.navRoute) {
                    BACK -> navController.popBackStack()
                    else -> navController.navigate(it.navRoute)
                }
            }
    }
}

/**
 * Handle global app navigation events if needed
 *
 * @return true if the action is handled, false otherwise
 */
fun handleAppNavIfNeeded(action: ScreenNavAction, appEventBus: EventBus<AppEvent>, scope: CoroutineScope): Boolean {
    when(action) {
        is NavigateBack -> {
            scope.launch { appEventBus.dispatch(AppEvent.NavigateEvent.NavigateBackEvent) }
        }
        is AppNavAction -> {
            scope.launch { appEventBus.dispatch(AppEvent.NavigateEvent(action.destNavRoute)) }
        }
        else -> return false
    }

    return true
}

