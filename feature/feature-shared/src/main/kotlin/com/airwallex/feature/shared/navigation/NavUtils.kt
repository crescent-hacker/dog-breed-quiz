package com.airwallex.feature.shared.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.airwallex.feature.shared.BuildConfig
import com.airwallex.core.designsystem.util.rememberLambda
import com.airwallex.core.designsystem.util.rememberLambdaWithInput
import com.airwallex.core.util.AppLogger

private const val NAV_LOGGING_TAG = "Navigation: "
private fun logNavigation(str: String) {
    if (BuildConfig.NAVIGATION_LOGGING) {
        AppLogger.debug(NAV_LOGGING_TAG + str)
    }
}

fun <T : NavRoute> NavGraphBuilder.composable(
    route: T,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable(route.route, arguments, deepLinks, content = content)

private const val IS_DIALOG_NAV_ARGUMENT_KEY = "isDialog"
fun <T : NavRoute> NavGraphBuilder.dialogComposable(
    route: T,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) = composable(
    route = route.route,
    arguments = listOf(
        navArgument(IS_DIALOG_NAV_ARGUMENT_KEY) {
            defaultValue = true
        }
    ),
    deepLinks = deepLinks,
    content = content
)

fun NavController.navigate(
    route: NavRoute,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    logNavigation("Navigate to route: ${route.route}, args: ${route.args}")
    return navigate(route.route, builder)
}

/**
 * remember the navigation lambda that passing into screen as a arg to avoid unnecessary recomposition
 *
 * @param destRoute the destination screen route
 * @param builder builder for nav options
 */
@Composable
fun rememberOnNav(navController: NavController, destRoute: NavRoute, builder: NavOptionsBuilder.() -> Unit = {}) =
    rememberLambda(navController) {
        navController.navigate(destRoute, builder)
    }

@Composable
fun rememberOnNavBackOnly(navController: NavController): () -> Unit =
    rememberLambda(navController) {
        navController.popBackStack()
    }

/**
 * remember the navigation lambda that passing into screen as a arg to avoid unnecessary recomposition
 *
 * @param resolveNavRoute base on screen result to resolve navigation route
 * @param builder builder for nav options
 */
@Composable
fun rememberOnNav(
    navController: NavController,
    resolveNavRoute: (ScreenNavAction) -> NavRoute,
    builder: NavOptionsBuilder.(ScreenNavAction) -> Unit = {}
) =
    rememberLambdaWithInput<ScreenNavAction, Unit>(navController, resolveNavRoute) { action ->
        val route = resolveNavRoute(action)
        route.takeIf { it != NavRoute.EMPTY }?.let {
            navController.navigate(it) {
                this.builder(action)
            }
        }
    }

/**
 * remember the navigation lambda that passing into screen as a arg to avoid unnecessary recomposition
 *
 * @param resolveNavRoute resolve the navigation route
 */
@Composable
fun rememberOnNav(
    navController: NavController,
    resolveNavRoute: () -> NavRoute,
    builder: NavOptionsBuilder.() -> Unit = {}
) =
    rememberLambda<Unit>(navController, resolveNavRoute) {
        val route = resolveNavRoute()
        route.takeIf { it != NavRoute.EMPTY }?.let {
            navController.navigate(it, builder)
        }
    }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: NavRoute,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        route = route,
        builder = builder
    )
}

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: NavRoute,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    logNavigation("NavHost is starting with: ${startDestination.route}, args: ${startDestination.args}")

    return androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        route = route,
        builder = builder,
        enterTransition = {
            if (this.initialState.destination.arguments.containsKey(IS_DIALOG_NAV_ARGUMENT_KEY)) {
                fadeIn(animationSpec = tween(400))
            } else {
                val navDirection = if (this.targetState.destination.arguments.containsKey(IS_DIALOG_NAV_ARGUMENT_KEY)) AnimatedContentTransitionScope.SlideDirection.Up
                else AnimatedContentTransitionScope.SlideDirection.Left
                slideIntoContainer(
                    towards = navDirection,
                    animationSpec = spring(
                        dampingRatio = DampingRatioNoBouncy,
                        stiffness = StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    )
                )
            }
        },
        exitTransition = {
            if (this.initialState.destination.arguments.containsKey(IS_DIALOG_NAV_ARGUMENT_KEY)) {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = spring(
                        dampingRatio = DampingRatioNoBouncy,
                        stiffness = StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    )
                )
            } else fadeOut(animationSpec = tween(10))
        },
        popEnterTransition = {
            if (this.targetState.destination.arguments.containsKey(IS_DIALOG_NAV_ARGUMENT_KEY)) {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = spring(
                        dampingRatio = DampingRatioNoBouncy,
                        stiffness = StiffnessMediumLow,
                        visibilityThreshold = IntOffset.VisibilityThreshold
                    )
                )
            } else fadeIn(animationSpec = tween(10))
        },
        popExitTransition = {
            val navDirection = if (this.initialState.destination.arguments.containsKey(IS_DIALOG_NAV_ARGUMENT_KEY)) AnimatedContentTransitionScope.SlideDirection.Down
            else AnimatedContentTransitionScope.SlideDirection.Right
            slideOutOfContainer(
                towards = navDirection,
                animationSpec = spring(
                    dampingRatio = DampingRatioNoBouncy,
                    stiffness = StiffnessMediumLow,
                    visibilityThreshold = IntOffset.VisibilityThreshold
                )
            )
        },
    )
}

fun NavGraphBuilder.navigation(
    startDestination: NavRoute,
    rootRoute: NavRoute,
    builder: NavGraphBuilder.() -> Unit
): Unit = navigation(
    startDestination = startDestination.route,
    route = rootRoute.route,
    builder = builder
)

fun NavController.popBackStack(
    route: NavRoute,
    inclusive: Boolean,
    saveState: Boolean = false
) {
    popBackStack(
        route = route.route,
        inclusive = inclusive,
        saveState = saveState
    )
}

fun NavController.popFullStack() {
    popBackStack(0, true)
}

/**
 * pop the current screen from backstack
 */
fun NavOptionsBuilder.popBackStack(
    navController: NavController
) {
    val route = navController.currentBackStackEntry?.destination?.route ?: return
    popUpTo(route) { inclusive = true }
}

/**
 * pop until certain route in backstack
 */
fun NavOptionsBuilder.popBackStack(
    route: NavRoute
) {
    popUpTo(route.route) { inclusive = true }
}

/**
 * pop all entries in current backstack
 */
fun NavOptionsBuilder.popFullStack() {
    popUpTo(0) { inclusive = true }
}

fun NavController.isInBackStack(routeId: String): Boolean = runCatching {
    getBackStackEntry(routeId)
}.isSuccess
